$(document).ready(function() {
    $('#login-form').submit(function(event) {
      event.preventDefault(); 
    
      var username = $('input[name="username"]').val();
      var password = $('input[name="password"]').val();
     
      var userData = {
        username: username,
        password: password
      };
  
      $.ajax({
        url: 'http://localhost:8080/api/v1/auth/authenticate', 
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(userData),
        success: function(response) {
          var token = response.data
          console.log(token)
          localStorage.setItem('token', token)
          alert('Inicio de sesión exitoso');
        },
        error: function(error) {
          
          alert('Error en el inicio de sesión');
        
        }
      });
    });
  });
  