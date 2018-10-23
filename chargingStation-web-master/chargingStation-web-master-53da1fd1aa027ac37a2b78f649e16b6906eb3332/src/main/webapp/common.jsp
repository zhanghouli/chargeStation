<%@ page import="com.jopool.chargingStation.www.enums.*" %>
<%@ page import="com.jopool.jweb.enums.SexEnum" %>
<%@ page import="com.jopool.jweb.enums.ModeEnum" %>
<%@ page import="com.jopool.chargingStation.www.base.helper.ApplicationConfigHelper" %>
<%@ page import="com.jopool.chargingStation.www.base.entity.Passport" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/fn.tld" prefix="fn" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="files" value="<%=ApplicationConfigHelper.getFilePath()%>"/>
<c:set var="ISDEV" value="<%=ApplicationConfigHelper.getMode()==ModeEnum.DEVELOP%>"/>
<c:set var="SUCCESS" value="1"/>
<!--用户状态-->
<c:set var="PASSPORT_STATUS" value="<%=PassportStatusEnum.values()%>"/>
<c:set var="PASSPORT_STATUS_NORMAL" value="<%=PassportStatusEnum.NORMAL%>"/>
<c:set var="PASSPORT_STATUS_DISABLE" value="<%=PassportStatusEnum.DISABLE%>"/>

<c:set var="PASSPORT_ADMIN" value="<%=PassportTypeEnum.ADMIN%>"/>
<c:set var="PASSPORT_OPERATOR" value="<%=PassportTypeEnum.OPERATOR%>"/>
<c:set var="PASSPORT_PROPERTY" value="<%=PassportTypeEnum.ESTATE%>"/>
<c:set var="PASSPORT_GOVERNMENT" value="<%=PassportTypeEnum.GOVERNMENT%>"/>
<c:set var="PASSPORT_CAROWNER" value="<%=PassportTypeEnum.CAROWNER%>"/>
<!-- int 类型状态-->
<c:set var="COMMON_STATUS" value="<%=CommonStatusEnum.values()%>"/>
<c:set var="COMMON_STATUS_NORMAL" value="<%=CommonStatusEnum.NORMAL%>"/>
<c:set var="COMMON_STATUS_DISABLE" value="<%=CommonStatusEnum.DISABLE%>"/>
<!-- 充电故障状态-->
<c:set var="WARNING_ENUM" value="<%=StationWarningEnum.values()%>"/>
<c:set var="WARNING_ENUM_TEMPERATURE_OVERRUN" value="<%=StationWarningEnum.TEMPERATURE_OVERRUN%>"/>
<c:set var="WARNING_ENUM_CHASSIS_OPENED" value="<%=StationWarningEnum.CHASSIS_OPENED%>"/>
<c:set var="WARNING_ENUM_POWER_FAILURE" value="<%=StationWarningEnum.POWER_FAILURE%>"/>
<c:set var="WARNING_ENUM_SMOKE_WARNING" value="<%=StationWarningEnum.SMOKE_WARNING%>"/>
<!-- 充电故障状态-->
<c:set var="STATION_STATUS" value="<%=StationStatusEnum.values()%>"/>
<c:set var="STATION_STATUS_NORMAL" value="<%=StationStatusEnum.NORMAL%>"/>
<c:set var="STATION_STATUS_DISABLE" value="<%=StationStatusEnum.DISABLE%>"/>
<c:set var="STATION_STATUS_FAULT" value="<%=StationStatusEnum.FAULT%>"/>
<!--是否 可用 -->
<c:set var="COMMON_STATUS_YES_OR_NO" value="<%=CommonYesOrNoEnum.values()%>"/>
<c:set var="COMMON_STATUS_YES" value="<%=CommonYesOrNoEnum.YES%>"/>
<c:set var="COMMON_STATUS_NO" value="<%=CommonYesOrNoEnum.NO%>"/>
<!--是否 开启 -->
<c:set var="COMMON_OPEN_OR_CLOSE" value="<%=CommonOpenOrCloseEnum.values()%>"/>
<c:set var="COMMON_OPEN" value="<%=CommonOpenOrCloseEnum.OPEN%>"/>
<c:set var="COMMON_CLOSE" value="<%=CommonOpenOrCloseEnum.CLOSE%>"/>

<!-- 充电站电口 状态 -->
<c:set var="STATION_PORT_STATUS" value="<%=StationPortStatusEnum.values()%>"/>
<c:set var="STATION_PORT_STATUS_FREE" value="<%=StationPortStatusEnum.FREE%>"/>
<c:set var="STATION_PORT_STATUS_WORKING" value="<%=StationPortStatusEnum.WORKING%>"/>
<c:set var="STATION_PORT_STATUS_DISABLE" value="<%=StationPortStatusEnum.DISABLE%>"/>
<c:set var="STATION_PORT_STATUS_FAULT" value="<%=StationPortStatusEnum.FAULT%>"/>
<c:set var="STATION_PORT_STATUS_SUSPEND" value="<%=StationPortStatusEnum.SUSPEND%>"/>
<!--plat form-->
<c:set var="PLAT_FORM" value="<%=PlatformEnum.values()%>"/>

<!-- FEED -->
<c:set var="FEED_BACK_ISVIEWED" value="<%=FeedBackIsViewedEnum.values()%>"/>
<!--feedback type-->
<c:set var="FEED_BACK" value="<%=FeedBackTypeEnum.values()%>"/>
<c:set var="FEED_BACK_ALL" value="<%=FeedBackTypeEnum.ALL%>"/>
<c:set var="FEED_BACK_CAROWNER" value="<%=FeedBackTypeEnum.CAROWNER%>"/>
<c:set var="FEED_BACK_OPERATOR" value="<%=FeedBackTypeEnum.OPERATOR%>"/>
<c:set var="FEED_BACK_PROPERTY" value="<%=FeedBackTypeEnum.ESTATE%>"/>
<c:set var="FEED_BACK_GOVERNMENT" value="<%=FeedBackTypeEnum.GOVERNMENT%>"/>
<!-- account type-->
<c:set var="ACCOUNT" value="<%=PassportAccountLogTypeEnum.values()%>"/>
<c:set var="ACCOUNT_ALL" value="<%=PassportAccountLogTypeEnum.ALL%>"/>
<c:set var="ACCOUNT_RECHARGE" value="<%=PassportAccountLogTypeEnum.RECHARGE%>"/>
<c:set var="ACCOUNT_RETURN" value="<%=PassportAccountLogTypeEnum.RETURN%>"/>
<c:set var="ACCOUNT_PAY" value="<%=PassportAccountLogTypeEnum.PAY%>"/>
<c:set var="ACCOUNT_WITHDRAWALS" value="<%=PassportAccountLogTypeEnum.WITHDRAWALS%>"/>
<c:set var="ACCOUNT_INCOME" value="<%=PassportAccountLogTypeEnum.INCOME%>"/>

<!--订单状态-->
<c:set var="ORDER_STATUS" value="<%=OrderStatusEnum.values()%>"/>
<c:set var="ORDER_STATUS_WAITING_FOR_PAY" value="<%=OrderStatusEnum.WAITING_FOR_PAY%>"/>
<c:set var="ORDER_STATUS_WAITING_FOR_CONNECT" value="<%=OrderStatusEnum.WAITING_FOR_CONNECT%>"/>
<c:set var="ORDER_STATUS_RECHARGING" value="<%=OrderStatusEnum.RECHARGING%>"/>
<c:set var="ORDER_STATUS_FINISHED" value="<%=OrderStatusEnum.FINISHED%>"/>
<c:set var="ORDER_STATUS_CLOSE" value="<%=OrderStatusEnum.CLOSE%>"/>

<!--微信模版-->
<c:set var="MESSAGE" value="<%=MessageTemplateEnum.values()%>"/>

