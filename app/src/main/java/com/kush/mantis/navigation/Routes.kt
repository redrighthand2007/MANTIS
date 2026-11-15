package com.kush.mantis.navigation

import kotlinx.serialization.Serializable

@Serializable object BasicRoute
@Serializable object ScientificRoute
@Serializable object ProgrammerRoute
@Serializable object ConverterRoute

/** Tab order, used for the bottom bar and for slide direction between screens. */
internal val TabRoutes: List<Any> = listOf(BasicRoute, ScientificRoute, ProgrammerRoute, ConverterRoute)

/** Navigation stores a destination's route as the qualified class name of its route object. */
internal fun routeName(route: Any): String = route::class.qualifiedName.orEmpty()

internal fun tabIndexOf(route: String?): Int = TabRoutes.indexOfFirst { routeName(it) == route?.substringBefore("?") }
