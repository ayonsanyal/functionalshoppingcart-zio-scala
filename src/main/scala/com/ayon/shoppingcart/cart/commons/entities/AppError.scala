package com.ayon.shoppingcart.cart.commons.entities

abstract class AppError(val message: String) extends Throwable

case class DataValidationError(override val message: String) extends AppError(message)

case class DatabaseAccessError(override val message: String) extends AppError(message)
