package br.com.kotlin.erudio.service


import br.com.kotlin.erudio.config.FileStorageConfig
import br.com.kotlin.erudio.exception.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class FileStorageService @Autowired constructor(fileStorageConfig: FileStorageConfig) {

    private val fileStorageLocation: Path

    init {
        fileStorageLocation = Paths.get(fileStorageConfig.uploadDir).toAbsolutePath().normalize()
        try {
            Files.createDirectories(fileStorageLocation)
        } catch (e: Exception) {
            throw FileStorageException("Não foi possível criar o diretório onde os arquivos enviados serão armazenados", e)
        }
    }

    fun storeFile(file: MultipartFile): String {
        val fileName = StringUtils.cleanPath(file.originalFilename!!)
        return try {
            // my_file..txt
            if (fileName.contains(".."))
                throw FileStorageException("Erro. O nome do arquivo contém sequência de caminho inválida: $fileName")
            val targetLocation = fileStorageLocation.resolve(fileName)
            Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
            fileName
        } catch (e: Exception) {
            throw FileStorageException("Não foi possível armazenar o arquivo $fileName. Tente novamente!", e)
        }
    }

    fun loadFileAsResource(fileName: String): Resource {
        return try {
            val filePath = fileStorageLocation.resolve(fileName).normalize()
            val resource: Resource = UrlResource(filePath.toUri())
            if (resource.exists()) resource
            else throw MyFileNotFoundException("Arquivo não encontrado $fileName!")
        } catch (e: Exception) {
            throw MyFileNotFoundException("Arquivo não encontrado $fileName!", e)
        }
    }
}