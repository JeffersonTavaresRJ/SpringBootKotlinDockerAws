package br.com.kotlin.erudio.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name="users")
class User: UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long=0;

    @Column(name = "user_name", length = 255, unique = true)
    var userName: String?=null;

    @Column(name = "full_name", length = 255)
    var fullName: String?=null;

    @Column(name = "password", length = 255)
    private var password: String? = null;

    @Column(name = "account_non_expired")
    private var accountNonExpired: Boolean?=null;

    @Column(name = "account_non_locked")
    private var accountNonLocked: Boolean?=null;

    @Column(name = "credentials_non_expired")
    private var credentialsNonExpired: Boolean?=null;

    @Column(name = "enabled")
    private var enabled: Boolean?=null;


    //configurações da var permissions..
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_permission",
        joinColumns = [JoinColumn(name = "id_user")],
        inverseJoinColumns = [JoinColumn(name = "id_permission")]
    )
    var permissions: List<Permission>?=null;

    val roles: List<String>
        get(){
            val roles: MutableList<String> = ArrayList();
            for (permission in permissions!!){
                roles.add(permission.description)
            }
            return roles;
        }

    override fun getAuthorities(): Collection<out GrantedAuthority> {
        return permissions!!;
    }

    override fun getPassword(): String {
        return password!!;
    }

    override fun getUsername(): String {
       return userName!!;
    }

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired!!;
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked!!;
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired!!;
    }

    override fun isEnabled(): Boolean {
        return enabled!!;
    }
}