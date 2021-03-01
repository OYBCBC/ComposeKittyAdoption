package com.example.androiddevchallenge

import java.util.*

class AdoptionInformation(adoptable: Adoptable)

/**
 * 领养子类
 */
open class Adoptable(var category: String, var birthDate: Date, var description: String, var adoptionRequirement: List<String>, var imgList: List<String>)

//class Kitty(name: String, birthDate: Date, description: String, , ) :
//    Adoptable(name, birthDate, description,)