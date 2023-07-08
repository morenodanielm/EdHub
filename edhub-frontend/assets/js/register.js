$(document).ready(function() {
  $('form').submit(function(event) {
    event.preventDefault();

   
    var username = $('input[name="username"]').val();
    var email = $('input[name="email"]').val();
    var password = $('input[name="password"]').val();

   
    var userData = {
      username: username,
      email: email,
      password: password
    };

   
    $.ajax({
      url: 'http://localhost:8080/api/v1/auth/register',
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify(userData),
      success: function(response) {
        var token = response.data
        console.log(token)
        localStorage.setItem('token', token)
        alert('Registro exitoso');
        window.location.href = url
      },
      error: function(error) {
        
        alert('Error en el registro');
       
      }
    });
  });
});
