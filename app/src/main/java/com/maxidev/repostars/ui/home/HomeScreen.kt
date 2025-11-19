package com.maxidev.repostars.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maxidev.repostars.R
import com.maxidev.repostars.ui.theme.backgroundGradientDark
import com.maxidev.repostars.ui.theme.backgroundGradientLight
import kotlinx.serialization.Serializable

@Serializable data object HomeView

fun NavGraphBuilder.homeDestination(
    navigateToSearch: () -> Unit
) {
    composable<HomeView> {

        ScreenContent(navigateToSearch = navigateToSearch)
    }
}

@Composable
private fun ScreenContent(
    navigateToSearch: () -> Unit
) {
    val backgroundMode =
        if (isSystemInDarkTheme()) backgroundGradientDark else backgroundGradientLight

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundMode),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WelcomeLogo()

        Spacer(modifier = Modifier.padding(16.dp))

        OnboardingText()

        Spacer(modifier = Modifier.padding(16.dp))

        SearchButton(navigateToSearch = navigateToSearch)
    }
}

@Composable
private fun WelcomeLogo() {
    val image = painterResource(R.drawable.octocat)

    Box(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Image(
            painter = image,
            contentDescription = "App logo",
            modifier = Modifier
                .padding(8.dp)
                .size(200.dp)
        )
    }
}

@Composable
private fun OnboardingText() {
    val textResource = stringResource(R.string.onboarding_text)

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = textResource,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun SearchButton(
    navigateToSearch: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Button(
            onClick = navigateToSearch,
            shape = RoundedCornerShape(5.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search"
            )
            Spacer(modifier = Modifier.padding(ButtonDefaults.IconSpacing))
            Text(
                text = "Search",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun WelcomeScreenPreview() {
    val backgroundMode =
        if (isSystemInDarkTheme()) backgroundGradientDark else backgroundGradientLight

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundMode),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WelcomeLogo()

        Spacer(modifier = Modifier.padding(16.dp))

        OnboardingText()

        Spacer(modifier = Modifier.padding(16.dp))

        SearchButton(navigateToSearch = {})
    }
}