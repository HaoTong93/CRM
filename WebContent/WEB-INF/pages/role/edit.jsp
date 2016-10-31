<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<title>角色管理 - 分配权限</title>
	<script type="text/javascript">
		$(function(){
			$("div :checkbox[name!='authorityIds']").click(function(){
				var flag = $(this).is(":checked");
				
				$(this).parent("div").find(":checkbox[name='authorityIds']").prop("checked", flag);
			});
			
			$("div :checkbox[name='authorityIds']").click(function(){
				var $div = $(this).parents("div");
				
				var flag = 
					($div.find(" :checkbox[name='authorityIds']").length == 
						$div.find(" :checkbox[name='authorityIds']:checked").length
					);
				$div.find(":checkbox[name!='authorityIds']").prop("checked", flag);
			});
			
			$("div").each(function(){
				var flag = 
					($(this).find(" :checkbox[name='authorityIds']").length == 
						$(this).find(" :checkbox[name='authorityIds']:checked").length
					);
				$(this).find(":checkbox[name!='authorityIds']").prop("checked", flag);
			});
			
		})
	</script>
</head>

<body class="main">
 	
 	<form:form action="${ctp }/role/${role.id }" method="PUT" modelAttribute="role">
		<div class="page_title">
			角色管理 &gt; 分配权限
		</div>
		
		<div class="button_bar">
			<button class="common_button" onclick="javascript:history.back(-1);">
				返回
			</button>
			<button class="common_button" onclick="document.forms[0].submit();">
				保存
			</button>
		</div>

		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th class="input_title" width="10%">
					角色名
				</th>
				<td class="input_content" width="20%">
					${role.name }
				</td>
				<th class="input_title" width="10%">
					角色描述
				</th>
				<td class="input_content" width="20%">
					${role.description }
				</td>
				<th class="input_title" width="10%">
					状态
				</th>
				<td class="input_content" width="20%">
					${role.enabled ? '有效':'无效' }
				</td>
			</tr>
			<tr>
				<th class="input_title">
					权限
				</th>
				<td class="input_content" colspan="5" valign="top">
					<c:forEach items="${parentAuthorities }" var="p">
						<div>
							<input type="checkbox" class="parent"/>${p.displayName }
							营销管理:
							<br>
							
							&nbsp;&nbsp;&nbsp;
							<form:checkboxes items="${p.subAuthorities }" 
								path="authorityIds" 
								itemLabel="displayName" 
								itemValue="id"
								delimiter="<br>&nbsp;&nbsp;&nbsp;&nbsp;"/>						
							<br><br>
						</div>
					</c:forEach>
				</td>
			</tr>
		</table>
 	</form:form>
	
</body>
</html>
