import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ersubhadip.branchinternationalassignment.ui.theme.BluePrimary
import com.ersubhadip.branchinternationalassignment.ui.theme.LexendDecaLight
import com.ersubhadip.branchinternationalassignment.ui.theme.White

@Composable
fun LoadingButton(isLoading: Boolean = false, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.then(Modifier.size(24.dp)), color = White)
        } else {
            Text(text = "Login", fontSize = 16.sp, fontFamily = LexendDecaLight)
        }
    }
}