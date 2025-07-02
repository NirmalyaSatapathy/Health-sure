<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<f:view>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Patient Insurance Details</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8fcff;
            color: #003366;
            margin: 0;
            padding: 20px;
        }
        h2 {
            color: #0077b6;
            text-align: center;
            margin-bottom: 25px;
        }
        .data-table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
            background-color: #ffffff;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        }
        .data-table th, .data-table td {
            border: 1px solid #bcd9ea;
            padding: 10px;
            text-align: left;
        }
        .data-table th {
            background-color: #d0f0f3;
            color: #003c58;
        }
        .data-table tr:nth-child(even) {
            background-color: #f1faff;
        }
        .data-table tr:hover {
            background-color: #e0f7ff;
        }
        .error-message {
            color: red;
            font-size: 0.9em;
        }
    </style>
</head>
<body>

<h:form prependId="false">
    <h2>Show Insurance Details of Patient</h2>
    <h:panelGrid columns="3" cellpadding="5">
        <h:outputLabel for="doctorId" value="Enter Doctor ID:" />
        <h:inputText id="doctorId" value="#{providerController.doctorId}" required="true" />
        <h:message for="doctorId" styleClass="error-message" />

        <h:outputLabel for="recipientId" value="Enter Patient ID (optional):" />
        <h:inputText id="recipientId" value="#{providerController.healthId}" />
        <h:message for="recipientId" styleClass="error-message" />

        <h:outputLabel />
        <h:commandButton value="Search" action="#{providerController.handleSearch}" />
    </h:panelGrid>
</h:form>
<h:form rendered="#{not empty providerController.associatedPatients and  empty providerController.patientInsuranceList}">
 <h:panelGroup rendered="#{not empty providerController.topMessage and empty providerController.patientInsuranceList}">
        <h:outputText value="#{providerController.topMessage}" style="color:red; font-weight:bold;" />
        <br/><br/>
    </h:panelGroup>
    <h:dataTable value="#{providerController.associatedPatients}" var="patient" styleClass="data-table">
    <h:column>
            <f:facet name="header"><h:outputText value="Health Id"/></f:facet>
            <h:outputText value="#{patient.hId}" />
        </h:column>
        <h:column>
            <f:facet name="header"><h:outputText value="User name"/></f:facet>
            <h:outputText value="#{patient.userName}" />
        </h:column>
        <h:column>
            <f:facet name="header"><h:outputText value="First name"/></f:facet>
            <h:outputText value="#{patient.firstName}" />
        </h:column>
        <h:column>
            <f:facet name="header"><h:outputText value="Last name"/></f:facet>
            <h:outputText value="#{patient.lastName}" />
        </h:column>
       <h:column>
    <f:facet name="header"><h:outputText value="Show Insurance"/></f:facet>
        <h:commandButton value="Show Insurance"
                         action="#{providerController.showInsuranceForPatient(patient.hId)}"/>
</h:column>

    </h:dataTable>
</h:form>
<h:form>
    <h:dataTable value="#{providerController.patientInsuranceList}" var="insurance" styleClass="data-table"
                 rendered="#{not empty providerController.patientInsuranceList}">
        <h:column>
            <f:facet name="header"><h:outputText value="Patient Name" /></f:facet>
            <h:outputText value="#{insurance.patientName}" />
        </h:column>

        <h:column>
            <f:facet name="header"><h:outputText value="Company Name" /></f:facet>
            <h:outputText value="#{insurance.companyName}" />
        </h:column>

        <h:column>
            <f:facet name="header"><h:outputText value="Plan Name" /></f:facet>
            <h:outputText value="#{insurance.planName}" />
        </h:column>

        <h:column>
            <f:facet name="header"><h:outputText value="Enrollment Date" /></f:facet>
            <h:outputText value="#{insurance.enrollmentDate}">
                <f:convertDateTime pattern="yyyy-MM-dd" />
            </h:outputText>
        </h:column>

        <h:column>
            <f:facet name="header"><h:outputText value="Coverage Start" /></f:facet>
            <h:outputText value="#{insurance.coverageStartDate}">
                <f:convertDateTime pattern="yyyy-MM-dd" />
            </h:outputText>
        </h:column>

        <h:column>
            <f:facet name="header"><h:outputText value="Coverage End" /></f:facet>
            <h:outputText value="#{insurance.coverageEndDate}">
                <f:convertDateTime pattern="yyyy-MM-dd" />
            </h:outputText>
        </h:column>

        <h:column>
            <f:facet name="header"><h:outputText value="Coverage Type" /></f:facet>
            <h:outputText value="#{insurance.coverageType}" />
        </h:column>

        <h:column>
            <f:facet name="header"><h:outputText value="Status" /></f:facet>
            <h:outputText value="#{insurance.coverageStatus}" />
        </h:column>

        <h:column>
            <f:facet name="header"><h:outputText value="Coverage Limit" /></f:facet>
            <h:outputText value="#{insurance.coverageLimit}" />
        </h:column>

        <h:column>
            <f:facet name="header"><h:outputText value="Remaining Amount" /></f:facet>
            <h:outputText value="#{insurance.remaining}" />
        </h:column>

        <h:column>
            <f:facet name="header"><h:outputText value="Claimed Amount" /></f:facet>
            <h:outputText value="#{insurance.claimed}" />
        </h:column>

        <h:column>
            <f:facet name="header"><h:outputText value="Last Claim Date" /></f:facet>
            <h:outputText value="#{insurance.lastClaimDate}">
                <f:convertDateTime pattern="yyyy-MM-dd" />
            </h:outputText>
        </h:column>

        <h:column>
            <f:facet name="header"><h:outputText value="Action" /></f:facet>
            <h:panelGroup rendered="#{insurance.coverageType eq 'FAMILY'}">
                <h:commandButton value="View Members" action="#{providerController.redirect(insurance)}" />
            </h:panelGroup>
        </h:column>
    </h:dataTable>
</h:form>

</body>
</html>
</f:view>
