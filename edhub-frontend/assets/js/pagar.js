$(document).ready(function() {
  $('#form-pagar').submit(function(event) {
      event.preventDefault(); 
    
      var orden = {
        precio: 10,
        moneda: "USD",
        intencion: "sale",
        descripcion: "Pago para obtener el perfil de tutor"
      };
  
      $.ajax({
        url: 'http://localhost:8080/api/v1/paypal/pagar', 
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(orden),
        success: function(response) {
          
        },
        error: function(error) {
          
          alert('Error');
        
        }
      });
    });
  });