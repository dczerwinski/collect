package org.odk.collect.android.regression;

import android.Manifest;

import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.odk.collect.android.support.pages.FormEntryPage;
import org.odk.collect.android.support.pages.GeneralSettingsPage;
import org.odk.collect.android.support.pages.MainMenuPage;
import org.odk.collect.android.support.CopyFormRule;
import org.odk.collect.android.support.ResetStateRule;

//Issue NODK-247
@RunWith(AndroidJUnit4.class)
public class FillBlankFormWithRepeatGroupTest extends BaseRegressionTest {

    @Rule
    public RuleChain copyFormChain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE)
            )
            .around(new ResetStateRule())
            .around(new CopyFormRule("TestRepeat.xml"))
            .around(new CopyFormRule("form1.xml"))
            .around(new CopyFormRule("form2.xml"))
            .around(new CopyFormRule("form3.xml"))
            .around(new CopyFormRule("form4.xml"))
            .around(new CopyFormRule("form5.xml"))
            .around(new CopyFormRule("form6.xml"))
            .around(new CopyFormRule("form7.xml"))
            .around(new CopyFormRule("form8.xml"))
            .around(new CopyFormRule("form9.xml"))
            .around(new CopyFormRule("RepeatGroupAndGroup.xml"))
            .around(new CopyFormRule("basic.xml"))
            .around(new CopyFormRule("repeat_group_form.xml"))
            .around(new CopyFormRule("repeat_group_new.xml"))
            .around(new CopyFormRule("RepeatTitles_1648.xml"));

    @Test
    public void whenNoRepeatGroupAdded_ShouldNotDoubleLastQuestion() {

        //TestCase1
        new MainMenuPage(rule)
                .startBlankForm("TestRepeat")
                .clickOptionsIcon()
                .clickGeneralSettings()
                .clickOnUserInterface()
                .clickNavigation()
                .clickUseSwipesAndButtons()
                .pressBack(new GeneralSettingsPage(rule))
                .pressBack(new FormEntryPage("TestRepeat", rule))
                .clickForwardButton()
                .clickOnDoNotAddGroup()
                .clickOnDoNotAddGroup()
                .clickForwardButton()
                .clickSaveAndExit();
    }

    @Test
    public void dynamicGroupLabel_should_beCalculatedProperly() {

        //TestCase3
        new MainMenuPage(rule)
                .startBlankForm("Repeat titles 1648")
                .inputText("test")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .inputText("FirstPerson")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .inputText("25")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .checkIsTextDisplayed("gr1 > 1 > Person: 25")
                .clickGoToArrow()
                .checkIsTextDisplayed("gr1 > 1 > Person: 25")
                .clickOnText("Photo")
                .swipeToNextQuestion()
                .clickOnDoNotAddGroup()
                .inputText("SecondPart")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .checkIsTextDisplayed("Part1 > 1 > Xxx: SecondPart")
                .clickGoToArrow()
                .checkIsTextDisplayed("Part1 > 1 > Xxx: SecondPart")
                .clickOnText("Date")
                .swipeToNextQuestion()
                .swipeToNextQuestion()
                .clickOnDoNotAddGroup()
                .clickSaveAndExit();
    }

    @Test
    public void nestedGroupsWithFieldListAppearance_ShouldBeAbleToFillTheForm() {

        //TestCase5
        new MainMenuPage(rule)
                .startBlankForm("form1")
                .swipeToNextQuestion()
                .swipeToNextQuestion()
                .clickSaveAndExit();

        new MainMenuPage(rule)
                .startBlankForm("form2")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .clickSaveAndExit();

        new MainMenuPage(rule)
                .startBlankForm("form3")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .clickSaveAndExit();

        new MainMenuPage(rule)
                .startBlankForm("form4")
                .inputText("T1")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .inputText("T2")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .inputText("T3")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .clickSaveAndExit();

        new MainMenuPage(rule)
                .startBlankForm("form5")
                .inputText("T1")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .inputText("T2")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .inputText("T3")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .clickSaveAndExit();

        new MainMenuPage(rule)
                .startBlankForm("form6")
                .inputText("T1")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .inputText("T2")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .inputText("T3")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .clickSaveAndExit();

        new MainMenuPage(rule)
                .startBlankForm("form7")
                .swipeToNextQuestion()
                .clickSaveAndExit();

        new MainMenuPage(rule)
                .startBlankForm("form8")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .clickSaveAndExit();

        new MainMenuPage(rule)
                .startBlankForm("form9")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .clickSaveAndExit();
    }

    @Test
    public void whenNoRepeatGroupAdded_ShouldBackwardButtonBeClickable() {

        //TestCase6
        new MainMenuPage(rule)
                .clickOnMenu()
                .clickGeneralSettings()
                .clickOnUserInterface()
                .clickNavigation()
                .clickUseSwipesAndButtons()
                .pressBack(new GeneralSettingsPage(rule))
                .pressBack(new MainMenuPage(rule))
                .startBlankFormWithRepeatGroup("RepeatGroupAndGroup", "G1")
                .clickOnDoNotAdd(new FormEntryPage("RepeatGroupAndGroup", rule))
                .closeSoftKeyboard()
                .clickBackwardButton()
                .clickOnDoNotAddGroup()
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .clickSaveAndExit();
    }

    @Test
    public void when_pageBehindRepeatGroupWithRegularGroupInsideIsVisible_should_swipeBackWork() {

        //TestCase7
        new MainMenuPage(rule)
                .startBlankFormWithRepeatGroup("RepeatGroupNew", "People")
                .clickOnAdd(new FormEntryPage("RepeatGroupNew", rule))
                .inputText("A")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .inputText("1")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .clickOnAddGroup()
                .inputText("B")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .inputText("2")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .clickOnAddGroup()
                .inputText("C")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .inputText("3")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .clickOnDoNotAddGroup()
                .swipeToPreviousQuestion()
                .checkIsTextDisplayed("3")
                .swipeToNextQuestion()
                .clickOnDoNotAddGroup()
                .clickSaveAndExit();
    }

    @Test
    public void when_navigateOnHierarchyView_should_breadcrumbPathBeVisible() {

        //TestCase8
        new MainMenuPage(rule)
                .startBlankFormWithRepeatGroup("RepeatGroupNew", "People")
                .clickOnAdd(new FormEntryPage("RepeatGroupNew", rule))
                .inputText("A")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .inputText("1")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .clickOnAddGroup()
                .inputText("B")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .inputText("2")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .clickOnAddGroup()
                .inputText("C")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .inputText("3")
                .clickGoToArrow()
                .checkIsTextDisplayed("People > 3 > Person: C")
                .clickGoUpIcon()
                .checkIsTextDisplayed("3.\u200E Person: C")
                .clickJumpEndButton()
                .clickSaveAndExit();
    }

    @Test
    public void firstQuestionWithLongLabel_ShouldDisplayBothAnswersInHierarchyPage() {

        //TestCase11
        new MainMenuPage(rule)
                .startBlankForm("basic")
                .inputText("1")
                .closeSoftKeyboard()
                .swipeToNextQuestion()
                .inputText("2")
                .closeSoftKeyboard()
                .clickGoToArrow()
                .checkIsTextDisplayed("2")
                .clickJumpEndButton()
                .clickSaveAndExit();
    }

    @Test
    public void openHierarchyPageFromLastView_ShouldNotDisplayError() {

        //TestCase12
        new MainMenuPage(rule)
                .startBlankFormWithRepeatGroup("Repeat Group", "Grp1")
                .clickOnAdd(new FormEntryPage("Repeat Group", rule))
                .swipeToNextQuestion()
                .clickOnDoNotAddGroup()
                .swipeToNextQuestion()
                .clickOnDoNotAddGroup()
                .clickGoToArrow()
                .clickJumpEndButton()
                .clickSaveAndExit();
    }
}