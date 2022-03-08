package com.example.azureobserver.myapplication.domain.model.entities

import org.simpleframework.xml.Element

import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class Article  @JvmOverloads constructor(

    @field:Element(name = "title")
    @param:Element(name = "title")
    var title: String? = null,


    @field:Element(name = "link")
    @param:Element(name = "link")
    var link: String? = null,


    @field:Element(name = "description")
    @param:Element(name = "description")
    var description: String? = null
)