package com.ewersson.dashboard_bi_api.service

import com.ewersson.dashboard_bi_api.model.products.ProductDTO
import com.ewersson.dashboard_bi_api.model.sales.SalesByStateDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.repositories.ProductRepository
import com.ewersson.dashboard_bi_api.repositories.SalesRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CommandService(
    private val productService: ProductService,

    private val salesRepository: SalesRepository,

    private val productRepository: ProductRepository
) {

    fun processCommand(command: String, authenticatedUser: User): ResponseEntity<Any> {
        val normalizedCommand = command.lowercase().trim()

        return when {
            normalizedCommand.contains("vendas de hoje") || normalizedCommand.contains("vendas de hj") -> ResponseEntity.ok(getSalesToday(authenticatedUser))

            normalizedCommand.contains("estoque do produto") ||
                    normalizedCommand.contains("preço do produto") ||
                         normalizedCommand.contains("produto ") -> {

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
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado tente novamente: $productName")
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


    fun getSalesToday(owner: User): List<SalesByStateDTO> {
        val today = LocalDate.now()
        val userProducts = productRepository.findByUserId(owner.id!!)

        val salesToday = salesRepository.findAll().filter { sale ->
            sale.date.toLocalDate() == today && sale.productName in userProducts.map { it.name }
        }
        return salesToday.groupBy { it.state }.map { (state, sales) ->
            SalesByStateDTO(
                state = state,
                totalQuantity = sales.sumOf { it.quantity ?: 0 },
                totalValue = sales.sumOf { (it.productPrice ?: 0.0) * (it.quantity ?: 0) },
                averagePrice = if (sales.isNotEmpty()) sales.mapNotNull { it.productPrice }.average() else 0.0
            )
        }
    }






}