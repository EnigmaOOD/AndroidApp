import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ir.enigma.app.ui.theme.IconSmall
import ir.enigma.app.R;
import ir.enigma.app.ui.theme.SpaceMedium

@Composable
fun getLableByInputType(keyboardType: KeyboardType): String {
    return when (keyboardType) {
        KeyboardType.Email -> "ایمیل"
        KeyboardType.Password, KeyboardType.NumberPassword -> "رمز عبور"
        else -> ""
    }
}

@Composable
fun getErrorByInputType(keyboardType: KeyboardType, label: String): String {
    return when (keyboardType) {
        KeyboardType.Email -> "فرمت ایمیل صحیح نیست"
        KeyboardType.Password, KeyboardType.NumberPassword -> "رمز عبور باید بیشتر از 8 کاراکتر داشته باشد"
        else -> "$label نمی\u200Cتواند خالی باشد"
    }
}

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    text: MutableState<String>,
    onValueChange: (String) -> Unit = { text.value = it },
    leadingIcon: ImageVector? = null,
    label: String = getLableByInputType(keyboardType),
    error: String = getErrorByInputType(keyboardType, label),
    showError: Boolean = false,
    hasError: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
    onAction: (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
) {
    val keyboardActions =
        if (onAction != null)
            KeyboardActions(onAny = { onAction?.invoke() })
        else
            KeyboardActions.Default
    when (keyboardType) {

        KeyboardType.Password ->
            PasswordTextField(
                password = text,
                leadingIcon = leadingIcon,
                onValueChange = onValueChange,
                label = label,
                isError = hasError,
                error = error,
                keyboardActions = keyboardActions,
                imeAction = imeAction,
                showError = showError
            )

        else -> GeneralTextField(
            modifier = modifier,
            text = text,
            onValueChange = onValueChange,
            isError = hasError,
            label = label,
            error = error,
            showError = showError,
            leadingIcon = leadingIcon,
            keyboardAction = keyboardActions,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            trailingIcon = trailingIcon,
        )
    }
}

@Composable
fun GeneralTextField(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    onValueChange: (String) -> Unit = { text.value = it },
    label: String,
    error: String,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions,
    isError: Boolean = text.value.isEmpty(),
    showError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardAction: KeyboardActions = KeyboardActions.Default,
) {


    OutlinedTextFieldValidation(
        modifier = modifier,
        value = text.value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        isError = isError,
        error = error,
        keyboardActions = keyboardAction,
        label = label, showError = showError,
        visualTransformation = visualTransformation,
        leadingIcon =
        if (leadingIcon != null) {
            @Composable {
                Icon(
                    modifier = Modifier.size(IconSmall),
                    imageVector = leadingIcon,
                    contentDescription = "$label icon"
                )
            }
        } else null,
        trailingIcon = trailingIcon,
    )
}


@Composable
fun PasswordTextField(
    password: MutableState<String>,
    label: String,
    error: String,
    onValueChange: (String) -> Unit = { password.value = it },
    leadingIcon: ImageVector?,
    imeAction: ImeAction,
    isError: Boolean = password.value.length < 8, showError: Boolean,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {

    val passwordVisible = remember { mutableStateOf(false) }

    GeneralTextField(
        text = password,
        onValueChange = onValueChange,
        label = label,
        error = error,
        leadingIcon = leadingIcon,
        isError = isError, showError = showError,
        keyboardAction = keyboardActions,
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        trailingIcon = {
            val image = if (passwordVisible.value)
                painterResource(R.drawable.ic_invisible_bold)
            else
                painterResource(R.drawable.ic_visible_bold)


            val description = if (passwordVisible.value) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(
                    painter = image,
                    description,
                    modifier = Modifier.size(IconSmall),
                )
            }

        }
    )
}

@Composable
fun OutlinedTextFieldValidation(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    showError: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: String,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    error: String = "",
    isError: Boolean = error.isNotEmpty(),
    errorIcon: @Composable (() -> Unit)? = {
        DefaultErrorIcon()
    },
    trailingIcon: @Composable (() -> Unit)?,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.medium,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()

) {

    val changeText = remember {
        mutableStateOf(false)
    }
    val displayError = isError && showError

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            enabled = enabled,
            readOnly = readOnly,
            value = value,

            onValueChange = {
                changeText.value = true
                onValueChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = singleLine,
            textStyle = textStyle,
            label = { Text(text = label) },
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = @Composable {
                if (displayError) {
                    if (errorIcon != null) {
                        errorIcon()
                    }
                } else if (trailingIcon != null) {
                    trailingIcon()
                }
            },
            isError = displayError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            maxLines = maxLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors
        )
        if (displayError) {
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = SpaceMedium, top = 0.dp)
            )
        } else {
            Text(
                text = "",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = SpaceMedium, top = 0.dp)
            )
        }
    }
}


@Composable
fun DefaultErrorIcon() {
    Icon(
        painter = painterResource(R.drawable.ic_info_bold),
        modifier = Modifier.size(IconSmall),
        tint = MaterialTheme.colors.error, contentDescription = "error"
    )
}


