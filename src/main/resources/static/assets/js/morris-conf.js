var Script = function () {

    //morris chart

    $(function () {
      // data stolen from http://howmanyleft.co.uk/vehicle/jaguar_'e'_type
     

      Morris.Donut({
        element: 'hero-donut',
        data: [
          {label: 'Jam', value: 25 },
          {label: 'Frosted', value: 40 },
          {label: 'Custard', value: 25 },
          {label: 'Sugar', value: 10 }
        ],
          colors: ['#3498db', '#2980b9', '#34495e'],
        formatter: function (y) { return y + "%" }
      });


      $('.code-example').each(function (index, el) {
        eval($(el).text());
      });
    });

}();




