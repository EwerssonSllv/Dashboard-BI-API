package com.ewersson.dashboard_bi_api.service

import com.ewersson.dashboard_bi_api.model.products.ProductDTO
import com.ewersson.dashboard_bi_api.model.sales.Sales
import com.ewersson.dashboard_bi_api.model.users.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CommandService(
    private val salesService: SalesService,
    private val productService: ProductService
) {

    fun processCommand(command: String, authenticatedUser: User): ResponseEntity<Any> {
        val normalizedCommand = command.lowercase().trim()

        return when {
            normalizedCommand.contains("vendas de hoje") -> ResponseEntity.ok(getSalesForToday())
            normalizedCommand.contains("estoque do produto") ||
                    normalizedCommand.contains("preço do produto") -> {

                val productName = extractProductName(normalizedCommand, "estoque do produto") ?:
                extractProductName(normalizedCommand, "preço do produto") ?:
                extractProductName(normalizedCommand, "vendas do produto")

                if (productName != null) {
                    val products: List<ProductDTO> = productService.getProductByName(productName, authenticatedUser)

                    return if (products.isNotEmpty()) {
                        val productDetails = products.map {
                            mapOf(
                                "nome" to it.name,
                                "imagem" to it.image,
                                "preço" to it.price,
                                "estoque" to it.stock
                            )
                        }
                        ResponseEntity.ok(productDetails)
                    } else {
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado: $productName")
                    }
                }
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nenhum nome de produto identificado.")
            }

            else -> {
                val productName = normalizedCommand.trim()
                if (productName.isNotEmpty()) {
                    val products: List<ProductDTO> = productService.getProductByName(productName, authenticatedUser)

                    return if (products.isNotEmpty()) {
                        val productDetails = products.map {
                            mapOf(
                                "nome" to it.name,
                                "imagem" to it.image,
                                "preço" to it.price,
                                "estoque" to it.stock
                            )
                        }
                        ResponseEntity.ok(productDetails)
                    } else {
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado: $productName")
                    }
                }
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comando não reconhecido.")
            }
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
    
}