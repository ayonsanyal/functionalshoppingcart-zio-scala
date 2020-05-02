package com.ayon.shoppingcart.cart.entities

import com.ayon.shoppingcart.cart.commons.entities.AppError

final case class ShoppingCartCreationError(override val message:String) extends AppError(message)
