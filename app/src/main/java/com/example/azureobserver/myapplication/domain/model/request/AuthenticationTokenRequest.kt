package com.example.azureobserver.myapplication.domain.model.request

data class AuthenticationTokenRequest(
    val grantType : String = "client_credentials",
    val clientId : String = "e0618152-2edc-4329-9997-f201adc85694",
    val clientSecret : String = "PUL7Q~bjuBlv8esPMg2qE8M_iVTOVW.PCNvka",
    val resource : String = "https://servicebus.azure.net"
)
