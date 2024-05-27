package br.com.kotlin.erudio

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder

@SpringBootApplication
class RestSpringBootAndKotlinApplication

fun main(args: Array<String>) {
	runApplication<RestSpringBootAndKotlinApplication>(*args);

	/*código para encriptar senhas no cadastro e login de usuários e depois gravar no banco*/
	/**
	val encoders: MutableMap<String, PasswordEncoder> = HashMap()
	encoders["pbkdf2"] = Pbkdf2PasswordEncoder()
	val passwordEncoder = DelegatingPasswordEncoder("pbkdf2", encoders)
	passwordEncoder.setDefaultPasswordEncoderForMatches(Pbkdf2PasswordEncoder())

	val result = passwordEncoder.encode("foo-bar")
	println("My hash $result")
	*/

	val encoders: MutableMap<String, PasswordEncoder> = HashMap();
	val pbkdF2Encoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
	encoders["pbkdf2"] = pbkdF2Encoder;
	val passwordEncoder = DelegatingPasswordEncoder("pbkdf2", encoders);
	passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdF2Encoder);

	val senha = "adminadmin";

	val result = passwordEncoder.encode(senha);
	println("My hash $result")

}
