package com.example.azureobserver.myapplication.domain.model.entities

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


@Root(name = "Login", strict = false)
data class ServiceBusMessageResponse @JvmOverloads constructor(
    @field:Element(name = "messageFromApplication")
    @param:Element(name = "messageFromApplication")
    var messageFromApplication: String? = null,

    @field:Element(name = "password")
    @param:Element(name = "password")
    var password: String? = null,
)
