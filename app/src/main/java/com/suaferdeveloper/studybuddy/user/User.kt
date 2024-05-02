package com.suaferdeveloper.studybuddy.user

/*
{
"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QucnUiLCJpYXQiOjE3MTQ1NzU0NjYsImV4cCI6MTcxNTg3MTQ2Nn0.B_y5x0s5ABImMdm8E8aMZvs4W2ivisCdl4OPsVDqY_8",
"user":{
"id":"67635b65-2106-4de1-b05d-bb9ebad741ff",
"email":"test@test.ru",
"phone":null,
"password":"$2a$10$WMe2RyRyc1y2meWjhQj8c.h0Timal5GJulvzPFlWBOQi.UgWKBcl6",
"name":"test@test.ru",
"imageUrl":null,
"department":null,
"specialty":null,
"group":null,
"studiedCourses":[],
"taughtCourses":[],
"role":"ADMIN",
"enabled":true,
"accountNonLocked":true,
"username":"test@test.ru",
"authorities":[{"authority":"ADMIN"}],
"credentialsNonExpired":true,
"accountNonExpired":true}}
 */

class User private constructor() {
    private var id: String = ""
    private var email: String = ""
    private var phone: String = ""
    private var password: String = ""
    private var name: String = ""
    private var imageUrl: String = ""
    private var department: String = ""
    private var specialty: String = ""
    private var group: Int? = null
    private var studiedCourses: String = ""
    private var taughtCourses: String = ""
    private var role: String = ""
    private var enabled: String = ""

    companion object {
        @Volatile
        private var instance: User? = null

        fun getInstance(): User {
            return instance ?: synchronized(this) {
                instance ?: User().also { instance = it }
            }
        }
    }

    fun updateFrom(newUser: User) {
        this.id = newUser.id
        this.email = newUser.email
        this.phone = newUser.phone
        this.password = newUser.password
        this.name = newUser.name
        this.department = newUser.department
        this.specialty = newUser.specialty
        this.group = newUser.group
        this.studiedCourses = newUser.studiedCourses
        this.taughtCourses = newUser.taughtCourses
        this.role = newUser.role
        this.enabled = newUser.enabled
    }

    fun getId(): String {
        return id
    }

    fun getEmail(): String {
        return email
    }

    fun getPhone(): String {
        return phone
    }

    fun getPassword(): String {
        return password
    }

    fun getName(): String {
        return name
    }

    fun getImageUrl(): String {
        return imageUrl
    }

    fun getDepartment(): String {
        return department
    }

    fun getSpecialty(): String {
        return specialty
    }

    fun getGroup(): Int? {
        return group
    }

    fun getStudiedCourses(): String {
        return studiedCourses
    }

    fun getTaughtCourses(): String {
        return taughtCourses
    }

    fun getRole(): String {
        return role
    }

    fun getEnabled(): String {
        return enabled
    }
}
