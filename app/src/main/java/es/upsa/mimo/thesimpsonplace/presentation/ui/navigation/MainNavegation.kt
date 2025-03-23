package es.upsa.mimo.thesimpsonplace.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.characterSection.CharacterFilterScreen
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.characterSection.CharactersFavScreen
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.menuSection.MenuScreen
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.quoteSection.QuotesScreen
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.episodeSection.EpisodesFilterScreen
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.episodeSection.EpisodesFavScreen
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.characterSection.CharactersScreen
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.episodeSection.EpisodeDetailScreen
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.profileSection.ProfileScreen
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.episodeSection.EpisodesScreen
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.profileSection.profileEdit.ProfileEditScreen
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.quoteSection.QuotesFavScreen
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.quoteSection.QuotesFilterScreen
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.quoteSection.gameQuotes.QuotesGameScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.quoteSection.gameQuotes.QuotesQuestionScreen
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.quoteSection.gameQuotes.QuotesResultScreen

// ✅ 1. Crear un sealed class para las rutas: en lugar de escribir las rutas como strings, se pueden definir en una sealed class (con parámetros si es necesario):
// • Evita errores tipográficos en las rutas.
// • Permite una navegación más clara y estructurada.
sealed class Screen(val route: String) {
    object Menu : Screen("menu")

    object Profile : Screen("profileScreen")
    object ProfileEdit : Screen("profileEditScreen")

    object AllCharacters : Screen("navigateToAllCharacter")
    object FilterCharacters : Screen("navigateToFilterCharacter")
    object FavoriteCharacters : Screen("navigateToFavoriteCharacter")

    object AllEpisodes : Screen("navigateToAllEpisodes")
    object FilterEpisodes : Screen("navigateToFilterEpisode")
    object FavoriteEpisodes : Screen("navigateToFavoriteEpisode")
    object EpisodeDetailStatic: Screen("episodeDetail/{id}")
    data class EpisodeDetail(val id: String) : Screen("episodeDetail/$id") // Pasando parámetros en la ruta

    object MainQuotes: Screen("navigateToAllQuote")
    object FilterQuotes : Screen("navigateToFilterQuotes")
    object FavoriteQuotes : Screen("navigateToFavoriteQuotes")
    object GameQuotes : Screen("navigateToGameQuotes")
    object QuestionQuotes : Screen("navigateToQuestionQuotes")
    object ResultQuotes : Screen("navigateToResultQuotes")
}

// ❌ Definición de destinos a través de objetos o clases (con parámetros que representan propiedades)
/** @Serializable
//class EpisodeDetailScreenDestination(val id: Int) // Destino de la vista de una de episodio en detalle
*/

@Composable
fun NavegacionApp() {

    val navController = rememberNavController()

    // ######################### TOTAL SCREENS 16 #########################
    NavHost(navController = navController,
        startDestination = "menu"){

        //________________________ MENU (SCREEN 1) ________________________
        composable(Screen.Menu.route) {
            MenuScreen(
                onUserProfile = { navController.navigate(Screen.Profile.route) },
                navigateToCharacters = { navController.navigate(Screen.AllCharacters.route) },
                navigateToEpisodes = { navController.navigate(Screen.AllEpisodes.route) },
                navigateToQuotes = { navController.navigate(Screen.MainQuotes.route) }
            )
        }

        //________________________ PROFILE (SCREENS 2) ________________________
        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigationProfileForm = { navController.navigate(Screen.ProfileEdit.route) },
                navigationArrowBack = { navigateTo( navController = navController, screen = Screen.Menu ) }
            )
        }

        composable(Screen.ProfileEdit.route) {
            ProfileEditScreen(
                onLogin = { navController.navigate(Screen.Profile.route) },
                navigationArrowBack = { navigateTo( navController = navController, screen = Screen.Menu ) }
            )
        }

        //________________________ CHARACTER (SCREENS 3) ________________________
        composable(Screen.AllCharacters.route) {
            CharactersScreen(
                navigateToFilterCharacters = { navController.navigate(Screen.FilterCharacters.route) },
                navigateToFavoriteCharacters = { navController.navigate(Screen.FavoriteCharacters.route) },
                navigationArrowBack = { navigateTo( navController = navController, screen = Screen.Menu ) }
            )
        }

        composable(Screen.FilterCharacters.route) {
            CharacterFilterScreen(
                navigateToAllCharacters = { navController.navigate(Screen.AllCharacters.route) },
                navigateToFavoriteCharacters = { navController.navigate(Screen.FavoriteCharacters.route) },
                navigationArrowBack = { navigateTo( navController = navController, screen = Screen.Menu ) }
            )
        }

        composable(Screen.FavoriteCharacters.route) {
            CharactersFavScreen(
                navigateToAllCharacters = { navController.navigate(Screen.AllCharacters.route) },
                navigateToFilterCharacters = { navController.navigate(Screen.FilterCharacters.route) },
                navigationArrowBack = { navigateTo( navController = navController, screen = Screen.Menu ) }
            )
        }

        //________________________ EPISODE (SCREENS 4) ________________________
        composable(Screen.AllEpisodes.route) {
            EpisodesScreen(
                navigateToFilterEpisode = { navController.navigate(Screen.FilterEpisodes.route) },
                navigateToFavoriteEpisode = { navController.navigate(Screen.FavoriteEpisodes.route) },
                onEpisodeSelected = { id -> navController.navigate(Screen.EpisodeDetail(id).route) },
                navigationArrowBack = { navigateTo( navController = navController, screen = Screen.Menu ) }
            )
        }

        composable(Screen.FilterEpisodes.route) {
            EpisodesFilterScreen(
                navigateToAllEpisodes = { navController.navigate(Screen.AllEpisodes.route) },
                navigateToFavoriteEpisode = { navController.navigate(Screen.FavoriteEpisodes.route) },
                onEpisodeSelected = { id -> navController.navigate(Screen.EpisodeDetail(id).route) },
                navigationArrowBack = { navigateTo( navController = navController, screen = Screen.Menu ) }
            )
        }

        composable(Screen.FavoriteEpisodes.route) {
            EpisodesFavScreen(
                navigateToAllEpisodes = { navController.navigate(Screen.AllEpisodes.route) },
                navigateToFilterEpisode = { navController.navigate(Screen.FilterEpisodes.route) },
                onEpisodeSelected = { id -> navController.navigate(Screen.EpisodeDetail(id).route) },
                navigationArrowBack = { navigateTo( navController = navController, screen = Screen.Menu ) }
            )
        }

        // ✅ 2. Antes pasaba el id del episodio a la 'EpisodeDetailScreen' con Serializable y toRoute(), lo cual es una estrategia válida, pero puede simplificarse usando la navegación de 'Jetpack Compose' de forma nativa.  Usando 'NavArgument' para pasar parámetros.
        // route = Contiene un parámetro dinámico dentro de la ruta "{id}", que será reemplazado por un valor real en tiempo de ejecución.
        // arguments = Espera un argumento llamado "id", que debe ser de tipo Int.
        composable( route = Screen.EpisodeDetailStatic.route,
                    arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { navBackStackEntry -> // Es el objeto que contiene la información sobre la pantalla a la que se ha navegado. Permite acceder a los argumentos de la ruta.
            val id = navBackStackEntry.arguments?.getString("id") ?: "0" // Recupera el argumento "id" pasado en la navegación.

            EpisodeDetailScreen(
                id = id, // Llama a la pantalla EpisodeDetailScreen, pasándole el id obtenido de la navegación.
                navigationArrowBack = { navController.popBackStack() } // volver a la pantalla anterior en la pila de navegación. Elimina la pantalla actual de la pila de navegación y vuelve a la anterior. Si la pantalla actual fue la primera de la pila, no hace nada (no crashea).
            )
        }
        /**
        composable<EpisodeDetailScreenDestination>{  navBackStackEntry /* destino */ ->
            val episodeDetailScreenDestination: EpisodeDetailScreenDestination = navBackStackEntry.toRoute() // Obtenemos el objeto

            val id: Int = episodeDetailScreenDestination.id

            EpisodeDetailScreen(id = id)
        }
        */

        //________________________ QUOTE AND GAME (SCREENS 4-2) ________________________
        composable(Screen.MainQuotes.route) {
            QuotesScreen(
                navigateToFilterQuotes = { navController.navigate(Screen.FilterQuotes.route) },
                navigateToFavoriteQuotes = { navController.navigate(Screen.FavoriteQuotes.route) },
                navigateToGameQuotes = { navController.navigate(Screen.GameQuotes.route) },
                navigationArrowBack = { navigateTo( navController = navController, screen = Screen.Menu ) }
            )
        }

        composable(Screen.FilterQuotes.route) {
            QuotesFilterScreen(
                navigateToQuotes = { navController.navigate(Screen.MainQuotes.route) },
                navigateToFavoriteQuotes = { navController.navigate(Screen.FavoriteQuotes.route) },
                navigateToGameQuotes = { navController.navigate(Screen.GameQuotes.route) },
                navigationArrowBack = { navigateTo( navController = navController, screen = Screen.Menu ) }
            )
        }

        composable(Screen.FavoriteQuotes.route) {
            QuotesFavScreen(
                navigateToQuotes = { navController.navigate(Screen.MainQuotes.route) },
                navigateToFilterQuotes = { navController.navigate(Screen.FilterQuotes.route) },
                navigateToGameQuotes = { navController.navigate(Screen.GameQuotes.route) },
                navigationArrowBack = { navigateTo( navController = navController, screen = Screen.Menu ) }
            )
        }

        composable(Screen.GameQuotes.route) {
            QuotesGameScreen(
                navigateToQuotes = { navController.navigate(Screen.MainQuotes.route) },
                navigateToFilterQuotes = { navController.navigate(Screen.FilterQuotes.route) },
                navigateToFavoriteQuotes = { navController.navigate(Screen.FavoriteQuotes.route) },
                navigateToQuestionQuotes = { navController.navigate(Screen.QuestionQuotes.route) },
                navigationArrowBack = { navigateTo( navController = navController, screen = Screen.Menu ) }
            )
        }

        composable( Screen.QuestionQuotes.route) {
            QuotesQuestionScreen(
                navigateToResultQuotes = { navController.navigate(Screen.ResultQuotes.route) }
            )
        }

        composable(Screen.ResultQuotes.route) {
            QuotesResultScreen(
                navigateToQuotes = { navController.navigate(Screen.MainQuotes.route) }
            )
        }
    }
}

// Otra forma de reducir el código seria crear una función de navegación reutilizable (aplicada en 'navigationArrowBack', pero se podria en todas las vistas)
fun navigateTo(navController: NavHostController, screen: Screen) {
    navController.navigate(screen.route)
}

/**
@Composable
private fun episodeSelectId(navController: NavHostController): (Int) -> Unit = { id ->
    val navigateToDetailEpisode = EpisodeDetailScreenDestination(id = id)
    navController.navigate(navigateToDetailEpisode) // Navegamos a la pantalla de detalles
}
*/