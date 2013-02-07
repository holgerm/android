package com.qeevee.gq.tests;

import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.dom4j.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reflections.Reflections;

import edu.bonn.mobilegaming.geoquest.mission.MissionActivity;
import edu.bonn.mobilegaming.geoquest.ui.DefaultUIFactory;

@RunWith(GQTestRunner.class)
public class DefaultUIFactoryTests {

    // === TESTS FOLLOW =============================================

    @Test
    public void CheckThatCreatorMethodsForAllUIsAreImplemented() {
	// GIVEN:
	Set<Class<? extends MissionActivity>> missionTypes = collectAllMissionTypes();

	// WHEN:

	// THEN:
	for (Class<? extends MissionActivity> missionTypeUnderTest : missionTypes) {
	    shouldHaveCreatorMethod(missionTypeUnderTest);
	}
    }

    // === HELPER METHODS FOLLOW =============================================

    private static final String MISSION_UI_PACKAGE = "edu.bonn.mobilegaming.geoquest.ui";

    private
	    void
	    shouldHaveCreatorMethod(Class<? extends MissionActivity> missionTypeUnderTest) {
	// TODO Auto-generated method stub
	Method[] methods = DefaultUIFactory.class.getDeclaredMethods();
	boolean found = false;
	for (int i = 0; i < methods.length && !found; i++) {
	    Method currentMethod = methods[i];
	    found |= creatorMethodFitsFor(missionTypeUnderTest,
					  currentMethod);
	}
    }

    private
	    boolean
	    creatorMethodFitsFor(Class<? extends MissionActivity> missionTypeUnderTest,
				 Method currentMethod) {
	String missionTypeName = missionTypeUnderTest.getSimpleName();
	Class<?> expectedReturnType;
	try {
	    expectedReturnType = Class.forName(MISSION_UI_PACKAGE + "."
		    + missionTypeName + "UI");
	} catch (ClassNotFoundException e) {
	    fail("UI type " + MISSION_UI_PACKAGE + "." + missionTypeName + "UI"
		    + " missing!");
	    return false;
	}
	return nameFits(currentMethod,
			missionTypeName) && returnTypeFits(currentMethod,
							   expectedReturnType)
		&& argumentsFit(currentMethod,
				expectedReturnType);
    }

    private boolean argumentsFit(Method currentMethod,
				 Class<?> expectedReturnType) {
	Class<?>[] argumentTypes = currentMethod.getParameterTypes();
	return argumentTypes.length == 1
		&& argumentTypes[0].equals(Element.class);
    }

    private boolean returnTypeFits(Method currentMethod,
				   Class<?> expectedReturnType) {
	return currentMethod.getReturnType().equals(expectedReturnType);
    }

    private boolean nameFits(Method currentMethod,
			     String missionTypeName) {
	return currentMethod.getName()
		.equals("create" + missionTypeName + "UI");
    }

    /**
     * @return all concrete classes that are derived of MissionActivity and are
     *         non-deprecated.
     */
    private Set<Class<? extends MissionActivity>> collectAllMissionTypes() {
	Reflections reflections = new Reflections(MissionActivity
		.getPackageBaseName());
	Set<Class<? extends MissionActivity>> missionTypeCandidates = reflections
		.getSubTypesOf(MissionActivity.class);
	Set<Class<? extends MissionActivity>> concreteMissionTypes = new HashSet<Class<? extends MissionActivity>>();
	for (Iterator<Class<? extends MissionActivity>> iterator = missionTypeCandidates
		.iterator(); iterator.hasNext();) {
	    Class<? extends MissionActivity> currentMissionTypeCandidate = (Class<? extends MissionActivity>) iterator
		    .next();
	    if (isConcrete(currentMissionTypeCandidate)
		    && isNonDeprecated(currentMissionTypeCandidate)) {
		concreteMissionTypes.add(currentMissionTypeCandidate);
	    }
	}
	return concreteMissionTypes;
    }

    private
	    boolean
	    isNonDeprecated(Class<? extends MissionActivity> currentMissionTypeCandidate) {
	Annotation[] annotations = currentMissionTypeCandidate.getAnnotations();
	return !currentMissionTypeCandidate
		.isAnnotationPresent(Deprecated.class);
    }

    private
	    boolean
	    isConcrete(Class<? extends MissionActivity> currentMissionTypeCandidate) {
	return !Modifier.isAbstract(currentMissionTypeCandidate.getModifiers());
    }

}
