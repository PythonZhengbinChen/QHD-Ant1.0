	function getAdmin() {
		$.ajax({
			url: "/QHD-Ant/getAdmin.do",
			async: false,
			type: "GET",
			success: function(data) {
				$("#admin-name").html(data.name);
			}
		});
	}