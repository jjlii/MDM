package com.example.mdm_everis

import com.example.core.Constant
import org.junit.Test

import org.junit.Assert.*
import java.util.*

class ExtensionsKtTest {

    @Test
    fun splitWithSpaceBefore_WithSpace_TextBeforeSpace() {
        val text = "Test 1"
        val resExpected = "Test"
        val res = text.splitWithSpaceBefore()
        assertEquals(res,resExpected)
    }

    @Test
    fun splitWithSpaceBefore_StartWithSpace_EmptyText() {
        val text = " Test"
        val resExpected = ""
        val res = text.splitWithSpaceBefore()
        assertEquals(res,resExpected)
    }

    @Test
    fun splitWithSpaceBefore_EndWithSpace_OriginalTextWithOutSpace() {
        val text = "Test "
        val resExpected = "Test"
        val res = text.splitWithSpaceBefore()
        assertEquals(res,resExpected)
    }

    @Test
    fun splitWithSpaceBefore_NotSpace_NotTextMsg() {
        val text = "Test"
        val resExpected = "Not found delimiter"
        val res = text.splitWithSpaceBefore()
        assertEquals(res,resExpected)
    }

    @Test
    fun splitWithSpaceBefore_MoreThanOneSpace_TextBeforeFirstText() {
        val text = "This is a Test"
        val resExpected = "This"
        val res = text.splitWithSpaceBefore()
        assertEquals(res,resExpected)
    }

    @Test
    fun splitWithSpaceAfter_EndWithSpace_EmptyText() {
        val text = "Test "
        val resExpected = ""
        val res = text.splitWithSpaceAfter()
        assertEquals(res,resExpected)
    }

    @Test
    fun splitWithSpaceAfter_StartWithSpace_OriginalTextNotSpace() {
        val text = " Test"
        val resExpected = "Test"
        val res = text.splitWithSpaceAfter()
        assertEquals(res,resExpected)
    }

    @Test
    fun splitWithSpaceAfter_WithSpace_TextAfterSpace() {
        val text = "Test 1"
        val resExpected = "1"
        val res = text.splitWithSpaceAfter()
        assertEquals(res,resExpected)
    }

    @Test
    fun splitWithSpaceAfter_MoreThanOneSpace_TextAfterFirstSpace() {
        val text = "This is a Test"
        val resExpected = "is a Test"
        val res = text.splitWithSpaceAfter()
        assertEquals(res,resExpected)
    }

    @Test
    fun splitWithSpaceAfter_NotSpace_NotSpaceMsg() {
        val text = "Test"
        val resExpected = "Not found delimiter"
        val res = text.splitWithSpaceBefore()
        assertEquals(res,resExpected)
    }

    @Test
    fun stringDateToLong_onlyDate_dateInMill() {
        val stringDate = "08/01/2020"
        val resExpected : Long = 1578438000000
        val res  : Long = stringDate.stringDateToLong(Constant.DateFormat.DATE_WITHOUT_TIME)
        assertEquals(res,resExpected)
    }

    @Test
    fun stringDateToLong_withTime_dateWithTimeInMill() {
        val stringDate = "08/01/2020 09:00"
        val resExpected : Long = 1578470400000
        val res  : Long = stringDate.stringDateToLong(Constant.DateFormat.DATE_WITH_TIME)
        assertEquals(res,resExpected)
    }
    @Test
    fun convertLongToDate_onlyDate_dateInString() {
        val longDate : Long = 1578438000000
        val resExpected = "08/01/2020"
        val res : String = longDate.convertLongToDate(Constant.DateFormat.DATE_WITHOUT_TIME)
        assertEquals(res,resExpected)
    }

    @Test
    fun convertLongToDate_withTime_dateWithTime() {
        val longDate : Long = 1578470400000
        val resExpected = "08/01/2020 09:00"
        val res : String = longDate.convertLongToDate(Constant.DateFormat.DATE_WITH_TIME)
        assertEquals(res,resExpected)
    }
}