package com.example.tpapimcpserver

import io.modelcontextprotocol.server.McpServerFeatures.SyncPromptRegistration
import io.modelcontextprotocol.spec.McpSchema.*
import org.springframework.ai.tool.ToolCallback
import org.springframework.ai.tool.ToolCallbacks
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class McpServerConfig {

    @Bean
    fun toolCallbacks(myTools: MyTools): List<ToolCallback> {
        return ToolCallbacks.from(myTools).toList();
    }

    @Bean
    fun myPrompts(): List<SyncPromptRegistration> {
        val prompt = Prompt(
            "Park Hours", "Get hours of operation for a park",
            java.util.List.of(PromptArgument("Park", "The name of the park", true))
        )

        val promptRegistration = SyncPromptRegistration(
            prompt
        ) { getPromptRequest: GetPromptRequest ->
            var nameArgument = getPromptRequest.arguments()["Park"] as String?
            if (nameArgument == null) {
                nameArgument = "Disneyland"
            }
            val userMessage = PromptMessage(Role.USER, TextContent("What are the park hours for $nameArgument tomorrow?"))
            GetPromptResult("Park Hours", java.util.List.of(userMessage))
        }

        return java.util.List.of(promptRegistration)
    }

}