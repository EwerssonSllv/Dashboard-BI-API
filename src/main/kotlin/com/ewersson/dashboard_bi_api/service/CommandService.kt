package com.ewersson.dashboard_bi_api.service

import com.ewersson.dashboard_bi_api.model.sales.Sales
import com.ewersson.dashboard_bi_api.model.users.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class CommandService(
    private val salesService: SalesService,
    private val productService: ProductService
) {

    fun processCommand(command: String, authenticatedUser: User): ResponseEntity<Any> {
        val normalizedCommand = normalizeCommand(command)

        return when {
            normalizedCommand.contains("vendas de hoje") -> ResponseEntity.ok(getSalesForToday())

            normalizedCommand.contains("estoque do produto") -> {
                val productName = extractProductName(normalizedCommand, "estoque do produto")
                if (productName != null) {
                    val product = productService.getProductByName(productName, authenticatedUser)?.firstOrNull()
                    return if (product != null) {
                        ResponseEntity.ok("O estoque do produto ${product.name} é: ${product.stock}")
                    } else {
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado: $productName")
                    }
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nenhum nome de produto identificado.")
            }

            normalizedCommand.contains("preço do produto") -> {
                val productName = extractProductName(normalizedCommand, "preço do produto")
                if (productName != null) {
                    val product = productService.getProductByName(productName, authenticatedUser)?.firstOrNull()
                    return if (product != null) {
                        ResponseEntity.ok("O preço do produto ${product.name} é: R$ ${product.price}")
                    } else {
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado: $productName")
                    }
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nenhum nome de produto identificado.")
            }

            normalizedCommand.contains("vendas do produto") -> {
                val productName = extractProductName(normalizedCommand, "vendas do produto")
                if (productName != null) {
                    val sales = salesService.getSalesByProductName(productName, authenticatedUser)
                    return if (sales.isNotEmpty()) {
                        ResponseEntity.ok(sales)
                    } else {
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma venda encontrada para o produto: $productName")
                    }
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nenhum nome de produto identificado.")
            }

            else -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comando não reconhecido.")
        }
    }

    fun extractProductName(command: String, keyword: String): String? {
        return command.replace(keyword, "").trim().ifEmpty { null }
    }

    fun getSalesForToday(): List<Sales> {
        val today = LocalDate.now()

        return salesService.findAllSales().filter { sale ->
            sale.date.toLocalDate() == today
        }
    }

    private fun normalizeCommand(command: String): String {
        val abbreviations = mapOf(
            "hj" to "hoje",
            "qt" to "quantidade",
            "preço" to "valor",
            "prod" to "produto"
        )

        var normalized = command.trim().lowercase(Locale.getDefault())

        abbreviations.forEach { (abbr, full) ->
            normalized = normalized.replace(abbr, full)
        }

        return normalized
    }

}
