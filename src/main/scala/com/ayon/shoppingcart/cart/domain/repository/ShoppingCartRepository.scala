package com.ayon.shoppingcart.cart.domain.repository

import com.ayon.shoppingcart.cart.entities.{ItemInfo, Products, ShoppingCart, ShoppingCartCreationError}
import zio.{Has, IO, ZIO}

object ShoppingCartRepository {

  type ShoppingCartRepository = Has[ShoppingCartRepository.Service]

  trait Service {

    def addToCart(items: ItemInfo,
                  unitPrice: Double,
                  itemName: String, currency: String): IO[ShoppingCartCreationError, Products]

    def showCart(currency: String): IO[ShoppingCartCreationError, ShoppingCart]
  }

  //accessor methods

  def addToCart(
                 items: ItemInfo,
                 unitPrice: Double,
                 itemName: String,
                 currency: String
               ): ZIO[ShoppingCartRepository, ShoppingCartCreationError, Products] = {
    ZIO.accessM(env => env.get.addToCart(items, unitPrice, itemName, currency))
  }

  def showCart(currency: String): ZIO[ShoppingCartRepository, ShoppingCartCreationError, ShoppingCart] = {
    ZIO.accessM(env => env.get.showCart(currency))
  }
}

