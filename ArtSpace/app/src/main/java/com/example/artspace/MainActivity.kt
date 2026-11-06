package com.example.artspace

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp(artSpaceViewModel: ArtSpaceViewModel = viewModel()) {

    val uiState by artSpaceViewModel.uiState.collectAsState()
    val currentArtworkIndex = uiState.currentArtworkIndex

    val configuration = LocalConfiguration.current

    val isLandscape =
        configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            ArtworkDisplay(
                index = currentArtworkIndex,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(24.dp))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                ArtworkInfo(index = currentArtworkIndex)

                Spacer(modifier = Modifier.height(24.dp))

                NavigationButtons(
                    currentArtworkIndex = currentArtworkIndex,
                    onPrevious = { artSpaceViewModel.previousArtwork() },
                    onNext = { artSpaceViewModel.nextArtwork() }
                )
            }
        }

    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            ArtworkDisplay(
                index = currentArtworkIndex,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            ArtworkInfo(
                index = currentArtworkIndex
            )

            Spacer(modifier = Modifier.height(16.dp))

            NavigationButtons(
                currentArtworkIndex = currentArtworkIndex,
                onPrevious = { artSpaceViewModel.previousArtwork() },
                onNext = { artSpaceViewModel.nextArtwork() }
            )
        }
    }
}

@Composable
fun ArtworkDisplay(
    index: Int,
    modifier: Modifier = Modifier
) {

    val imageRes = when (index) {
        1 -> R.drawable.artwork1
        2 -> R.drawable.artwork2
        3 -> R.drawable.artwork3
        else -> R.drawable.artwork1
    }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "Artwork $index",
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray
            )
            .padding(8.dp),
        contentScale = ContentScale.Fit
    )
}

@Composable
fun ArtworkInfo(index: Int) {

    val title: String
    val artistAndYear: String

    when (index) {

        1 -> {
            title = "Сикстинская мадонна"
            artistAndYear = "Рафаэль (1513)"
        }

        2 -> {
            title = "Дама с горностаем"
            artistAndYear = "Леонардо да Винчи (1419)"
        }

        3 -> {
            title = "Итальянский полдень"
            artistAndYear = "Карл Брюллов (1829)"
        }

        else -> {
            title = "Сикстинская мадонна"
            artistAndYear = "Рафаэль (1513)"
        }
    }

    Surface(
        color = Color.White,
        border = BorderStroke(1.dp, Color.Gray),
        shape = RoundedCornerShape(0.dp)
    ) {

        Column(
            modifier = Modifier.padding(
                horizontal = 24.dp,
                vertical = 8.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = artistAndYear,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun NavigationButtons(
    currentArtworkIndex: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedButton(
            onClick = onPrevious,
            enabled = currentArtworkIndex > 1,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(0.dp),
            border = BorderStroke(1.dp, Color.Gray),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFFF0F0F0),
                contentColor = Color.Black,
                disabledContentColor = Color.Gray
            )
        ) {

            Text(
                text = "Previous",
                fontSize = 18.sp
            )
        }

        OutlinedButton(
            onClick = onNext,
            enabled = currentArtworkIndex < 3,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(0.dp),
            border = BorderStroke(1.dp, Color.Gray),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFFF0F0F0),
                contentColor = Color.Black,
                disabledContentColor = Color.Gray
            )
        ) {

            Text(
                text = "Next",
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {

    ArtSpaceTheme {
        ArtSpaceApp()
    }
}