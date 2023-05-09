package com.example.infomedicalstaff.ui.fragments.pdf

import com.example.infomedicalstaff.business.model.DocModel
import java.io.File

interface OnPdfSelectListener {
    fun onPdfSelected(file : DocModel)
}