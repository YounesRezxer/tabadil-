package com.example.convert

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

                MaterialTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color(0xFFF8FAFC)
                    ) {
                        ConverterApp()
                    }
                }
            }
        }
    }
}

@Composable
fun ConverterApp() {

    val vazirB = FontFamily(Font(R.font.vazir_b))
    val vazirL = FontFamily(Font(R.font.vazir_l))

    val context = LocalContext.current

    val primaryColor = Color(0xFF0F172A)   //  (Slate 900)
    val accentColor = Color(0xFFF59E0B)
    val surfaceColor = Color(0xFFFFFFFF)
    val textSecondary = Color(0xFF64748B)

    var inputToman by remember {
        mutableStateOf("")
    }

    var resultRialNumber by remember {
        mutableStateOf("")
    }

    var resultRialWords by remember {
        mutableStateOf("")
    }

    val convert = {
        if (inputToman.isNotBlank()) {
            val toman = inputToman.toLongOrNull()
            if (toman != null && toman >= 0) {
                val rial = toman * 10
                resultRialNumber = formatNumberWithCommas(rial) + " ریال"
                resultRialWords = convertNumberToPersianWords(rial) + " ریال"
            } else {
                resultRialNumber = "عدد نامعتبر"
                resultRialWords = "لطفاً یک عدد صحیح مثبت وارد کنید"
            }
        } else {
            resultRialNumber = ""
            resultRialWords = "لطفاً عدد تومان را وارد کنید"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "تبدیل تومان به ریال",
            fontSize = 28.sp,
            fontFamily = vazirB,
            fontWeight = FontWeight.Bold,
            color = primaryColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "عدد به تومان",
                    fontFamily = vazirL,
                    color = textSecondary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = inputToman,
                    onValueChange = { inputToman = it },
                    label = { Text("مثال: 15000", color = textSecondary) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = Color(0xFFCBD5E1),
                        focusedLabelColor = accentColor,
                        cursorColor = accentColor,
                        focusedTextColor = primaryColor,
                        unfocusedTextColor = primaryColor
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.End,
                        fontFamily = vazirB
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = convert,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = accentColor,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "تبدیل به ریال",
                fontSize = 18.sp,
                fontFamily = vazirB,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (resultRialNumber.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = surfaceColor),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(16.dp)

            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "نتیجه (عدد)",
                        fontSize = 12.sp,
                        fontFamily = vazirL,
                        color = textSecondary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = resultRialNumber,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = vazirB,
                        color = accentColor,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (resultRialWords.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = surfaceColor),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(16.dp)

            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "نتیجه (حروف)",
                        fontSize = 12.sp,
                        fontFamily = vazirL,
                        color = textSecondary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = resultRialWords,
                        fontSize = 16.sp,
                        lineHeight = 26.sp,
                        fontFamily = vazirB,
                        color = primaryColor,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(
            onClick = { openUrl(context, "https://github.com/YounesRezxer") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, accentColor.copy(alpha = 0.5f)),
            colors = ButtonDefaults.textButtonColors(
                contentColor = accentColor
            )
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "حمایت",
                modifier = Modifier.size(16.dp),
                tint = accentColor
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = " سازنده",
                fontSize = 13.sp,
                fontFamily = vazirL,
                fontWeight = FontWeight.Medium,
                color = accentColor,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


    }
}

fun formatNumberWithCommas(number: Long): String {
    return String.format("%,d", number).replace(",", "،")
}

fun convertNumberToPersianWords(number: Long): String {
    if (number == 0L) return "صفر"

    val units = arrayOf("", "یک", "دو", "سه", "چهار", "پنج", "شش", "هفت", "هشت", "نه")
    val teens = arrayOf(
        "ده",
        "یازده",
        "دوازده",
        "سیزده",
        "چهارده",
        "پانزده",
        "شانزده",
        "هفده",
        "هجده",
        "نوزده"
    )
    val tens = arrayOf("", "ده", "بیست", "سی", "چهل", "پنجاه", "شصت", "هفتاد", "هشتاد", "نود")
    val hundreds =
        arrayOf("", "یکصد", "دویست", "سیصد", "چهارصد", "پانصد", "ششصد", "هفتصد", "هشتصد", "نهصد")
    val scales = arrayOf("", "هزار", "میلیون", "میلیارد")

    fun convertThreeDigits(n: Int): String {
        if (n == 0) return ""
        val h = n / 100
        val t = (n % 100) / 10
        val u = n % 10
        val result = StringBuilder()
        if (h > 0) {
            result.append(hundreds[h])
        }
        if (t == 1) {
            if (result.isNotEmpty()) result.append(" و ")
            result.append(teens[u])
        } else {
            if (t > 0) {
                if (result.isNotEmpty()) result.append(" و ")
                result.append(tens[t])
            }
            if (u > 0) {
                if (result.isNotEmpty()) result.append(" و ")
                result.append(units[u])
            }
        }
        return result.toString()
    }

    var num = number
    var scaleIndex = 0
    val parts = mutableListOf<String>()

    while (num > 0) {
        val part = (num % 1000).toInt()
        if (part > 0) {
            val partStr = convertThreeDigits(part)
            val scaleStr = scales[scaleIndex]
            val combined = if (scaleStr.isNotEmpty()) "$partStr $scaleStr" else partStr
            parts.add(0, combined)
        }
        num /= 1000
        scaleIndex++
    }

    return parts.joinToString(" و ")
}

fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}
