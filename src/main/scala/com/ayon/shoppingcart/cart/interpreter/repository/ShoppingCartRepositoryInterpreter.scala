package com.ayon.shoppingcart.cart.interpreter.repository

import java.util

import com.ayon.shoppingcart.cart.domain.repository.ShoppingCartRepository
import com.ayon.shoppingcart.cart.entities.{ItemInfo, Products, ShoppingCart, ShoppingCartCreationError}
import zio.IO

import scala.collection.JavaConverters._

object ShoppingCartRepositoryInterpreter extends ShoppingCartRepository.Service {
  private val cart: java.util.Map[String, Products] = new util.HashMap()

  override def addToCart(items: ItemInfo, unitPrice: Double, itemName: String, currency: String): IO[ShoppingCartCreationError, Products] = {
    import items._

    if (cart.containsKey(itemCode)) {
      //Divided the update operation in a step by step process
      IO {
        createProductUpdated(cart.get(itemCode), items, unitPrice)
      } mapError (t => ShoppingCartCreationError(t.getMessage))
    } else {
      IO {
        val productMeta: Products = Products(items, itemName, unitPrice * items.quantity, currency)
        cart.put(items.itemCode, Products(items, itemName, unitPrice * items.quantity, currency))
        productMeta
      } mapError (t => ShoppingCartCreationError(t.getMessage))
    }
  }

  override def showCart(currency: String): IO[ShoppingCartCreationError, ShoppingCart] = {
    val result = for {
      showCart <- IO {
        val shoppingCart = cart.asScala.values.toList
        val calculatePrice: Double = shoppingCart.foldLeft(0.0)(_ + _.price)
        ShoppingCart(shoppingCart, calculatePrice, currency)
      }
      if cart.asScala.nonEmpty
    } yield showCart
    result.mapError(t => ShoppingCartCreationError(t.getMessage))
  }

  def createProductUpdated(productOld: Products, itemInfo: ItemInfo, unitPrice: Double): Products = {
    val updatedItem = productOld.itemInfo.copy(quantity = itemInfo.quantity)
    val totalPrice = unitPrice * productOld.price
    val updatedProduct = productOld.copy(itemInfo = updatedItem, price = totalPrice)
    cart.replace(itemInfo.itemCode, updatedProduct)
    updatedProduct
  }
}
