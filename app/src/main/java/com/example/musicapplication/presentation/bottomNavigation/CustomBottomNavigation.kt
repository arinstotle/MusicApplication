package com.example.musicapplication.presentation.bottomNavigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapplication.R
import com.example.musicapplication.navigation.Screen

@Composable
fun CustomBottomNavigation(
    currentScreenRoute : String,
    onItemSelected : (Screen) -> Unit,
) {
    val items = Screen.BottomScreens.list
    Row(
        modifier= Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item->
            CustomButtonNavigationItem(item = item,
                isSelected = item.route == currentScreenRoute) {
                onItemSelected(item)
            }
        }
    }
}

@Composable
fun CustomButtonNavigationItem(
    item : Screen,
    isSelected : Boolean,
    onClick : () -> Unit
)  {
    val background = if (isSelected)
        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    else
        Color.Transparent
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onTertiary
    else
        MaterialTheme.colorScheme.tertiary
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = onClick)
    ){
        Row(
            modifier= Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = ImageVector.vectorResource(id = item.icon),
                contentDescription = null,
                tint = contentColor,
            )
            Spacer(modifier = Modifier.width(4.dp))
            AnimatedVisibility(visible = isSelected) {
                Text(
                    text = item.title,
                    color = contentColor,
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                        ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
