package br.com.kotlin.erudio.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority

@Entity
@Table(name="permission")
class Permission: GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0;

    @Column(length = 180)
    var description: String="";
    override fun getAuthority(): String {
        return description;
    }
}