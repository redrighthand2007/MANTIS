package com.kush.mantis.navigation

import kotlinx.serialization.Serializable

@Serializable object BasicRoute
@Serializable object ScientificRoute
@Serializable object ProgrammerRoute
@Serializable object ConverterRoute

/** Navigation stores a destination's route as the qualified class name of its route object. */
internal fun routeName(route: Any): String = route::class.qualifiedName.orEmpty()
