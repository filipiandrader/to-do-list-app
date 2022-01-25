package com.far.to_dolistapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.far.to_dolistapp.ui.add_edit.AddEditTodoScreen
import com.far.to_dolistapp.ui.list.TodoListScreen
import com.far.to_dolistapp.ui.theme.ToDoListAppTheme
import com.far.to_dolistapp.util.Routes.ADD_TODO
import com.far.to_dolistapp.util.Routes.EDIT_TODO
import com.far.to_dolistapp.util.Routes.TODO_LIST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = TODO_LIST) {
                        composable(TODO_LIST) {
                            TodoListScreen(
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }

                        composable(
                            ADD_TODO.plus("{todoId}"),
                            arguments = listOf(
                                navArgument(name = "todoId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            AddEditTodoScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(
                            EDIT_TODO.plus("{todoId}"),
                            arguments = listOf(
                                navArgument(name = "todoId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            AddEditTodoScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}