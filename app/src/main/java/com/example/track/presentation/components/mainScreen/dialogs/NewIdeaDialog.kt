package com.example.track.presentation.components.mainScreen.dialogs

//@Composable
//fun NewIdeaDialog(value: String, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit) {
//
//    val txtFieldError = remember { mutableStateOf("") }
//    val txtField = remember { mutableStateOf(value) }
//
//    Dialog(onDismissRequest = { setShowDialog(false) }) {
//        Surface(
//            shape = RoundedCornerShape(16.dp),
//        ) {
//            Box(
//                contentAlignment = Layout.Alignment.Center
//            ) {
//                Column(modifier = Modifier.padding(20.dp)) {
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = "Set value",
//                            style = TextStyle(
//                                fontSize = 24.sp,
//                                fontFamily = FontFamily.Default,
//                                fontWeight = FontWeight.Bold
//                            )
//                        )
//                        Icon(
//                            imageVector = Icons.Filled.Cancel,
//                            contentDescription = "",
//                            tint = colorResource(R.color.darker_gray),
//                            modifier = Modifier
//                                .width(30.dp)
//                                .height(30.dp)
//                                .clickable { setShowDialog(false) }
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(20.dp))
//
//                    TextField(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .border(
//                                BorderStroke(
//                                    width = 2.dp,
//                                    color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.holo_green_light else R.color.holo_red_dark)
//                                ),
//                                shape = RoundedCornerShape(50)
//                            ),
//                        colors = TextFieldDefaults.textFieldColors(
//                            backgroundColor = Color.Transparent,
//                            focusedIndicatorColor = Color.Transparent,
//                            unfocusedIndicatorColor = Color.Transparent
//                        ),
//                        leadingIcon = {
//                            Icon(
//                                imageVector = Icons.Filled.Money,
//                                contentDescription = "",
//                                tint = colorResource(R.color.holo_green_light),
//                                modifier = Modifier
//                                    .width(20.dp)
//                                    .height(20.dp)
//                            )
//                        },
//                        placeholder = { Text(text = "Enter value") },
//                        value = txtField.value,
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                        onValueChange = {
//                            txtField.value = it.take(10)
//                        })
//
//                    Spacer(modifier = Modifier.height(20.dp))
//
//                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
//                        Button(
//                            onClick = {
//                                if (txtField.value.isEmpty()) {
//                                    txtFieldError.value = "Field can not be empty"
//                                    return@Button
//                                }
//                                setValue(txtField.value)
//                                setShowDialog(false)
//                            },
//                            shape = RoundedCornerShape(50.dp),
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(50.dp)
//                        ) {
//                            Text(text = "Done")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}