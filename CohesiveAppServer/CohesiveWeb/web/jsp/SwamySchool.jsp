<%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Css Library Starts--->
        <link rel="stylesheet" href="/css/library/bootstrap.min.css">
        <link rel="stylesheet" href="/css/library/bootstrap-grid.min.css">
        <!-- Css Library Ends--->
        <!-- Js Libarary Starts----->
        <script src="/js/js_library/jquery-3.3.1.min.js"></script>
        <script src="/js/js_library/bootstrap.min.js"></script>
        <link rel="stylesheet" href="/css/utils/ScreenTemplate.css">
        <link rel="stylesheet" href="/css/cohesivemainpage.css">
        <!-- Js Libarary Ends----->
          <script type="text/javascript" src="/js/school.js"></script>
        
    </head>

    <body id="SubScreenCtrl" class="cohesive_body">
        <%
        response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
        response.setHeader("Pragma","no-cache"); //Http 1.0
        response.setHeader("Expires", "-1"); //Proxies
       response.setHeader("X-XSS-Protection","1;mode=block");
       //response.setHeader("X-Frame-Options","ALLOW-FROM blob:https://cohesive.ibdtechnologies.com");
       response.setHeader("X-Frame-Options","SAMEORIGIN");

       response.setHeader("Content-Security-Policy","default-src 'self';script-src 'self';style-src 'unsafe-inline' 'self';base-uri 'none';form-action 'self';frame-ancestors 'self';frame-src 'self'");
      %>
            <div id="schoolContent" class="schoolContent">
                
                <div class="card">
                    <div class="list-group">
                        <a href="#" class="btn btn-light accordion" id="accordionExample" data-toggle="collapse" data-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">About </a>
                        <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
                            <div class="card-body Cohesive_cardbody">
                                <div data-spy="scroll" data-target="#navbar-example2" data-offset="0">
                                    <div id="Abt" class="carousel slide carousel-fade" data-ride="carousel">
                                        <h5 class="card-header">Swamy's School</h5>
                                        <div class="carousel-inner">
                                            <div class="carousel-item active">
                                                <img class="d-block cohesive_courosel " src="/img/swamy school 1.jpg" alt="First slide">
                                            </div>
                                            <div class="carousel-item">
                                                <img class="d-block cohesive_courosel" src="/img/Swamy school 2.jpg" alt="Second slide">
                                            </div>
                                            <div class="carousel-item">
                                                <img class="d-block cohesive_courosel" src="/img/swamy.jpg" alt="Third slide">
                                            </div>
                                        </div>
                                        <a class="carousel-control-prev" href="#carouselExampleFade" role="button" data-slide="prev">
                                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                            <span class="sr-only">Previous</span>
                                        </a>
                                        <a class="carousel-control-next" href="#carouselExampleFade" role="button" data-slide="next">
                                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                            <span class="sr-only">Next</span>
                                        </a>
                                    </div>
                                    <p class="card-text shadow-sm p-3 bg-white rounded"><em>Swamy's School emphasizes on moral, spiritual and
                      ethical development of children
                      Swamy's School was founded in 1995 and has successfully established itself as one of the premier institutions in Chennai.
                      The school emphasizes on moral, spiritual and ethical development of children.
                      The teaching methods adopted are varied and flexible. Apart from regular teaching and individual attention, a lot of group based activities are
                      organized which brings out team work and leadership qualities in children.</em>
                                    </p>
                                </div>
                                <h5 class="card-header">Vision</h5>
                                <p class="card-text shadow-sm p-3 bg-white rounded"><em>
                    To be among the best schools in the country, meeting the hightest standards in terms of quality of education and infrastructure and to groom students to become responsible citizens who will play a key role in shaping up a bright future for our country.
                    </em>
                                </p>
                                <h5 class="card-header">Mission</h5>
                                <p class="card-text shadow-sm p-3 bg-white rounded"><em>
                    To be among the best schools in the country, meeting the hightest standards in terms of quality of education and infrastructure and to groom students to become responsible citizens who will play a key role in shaping up a bright future for our country.
                    </em>
                                </p>
                            </div>
                        </div>
                        <a href="#" class="btn btn-light accordion" id="Facility" data-toggle="collapse" data-target="#collapse" aria-expanded="false" aria-controls="collapseOne">Facility</a>
                        <div id="collapse" class="collapse" aria-labelledby="headingOne" data-parent="#Facility">
                            <div class="card">
                                <img class="card-img-top cohesive_courosel" src="/img/facility.jpg" alt="Card image cap">
                                <div class="card-header">Computer Lab</div>
                            </div>
                            <div class="card">
                                <img class="card-img-top cohesive_courosel" src="/img/facility5.jpg" alt="Card image cap">
                                <div class="card-header">Physics Lab</div>
                            </div>
                            <div class="card">
                                <img class="card-img-top cohesive_courosel" src="/img/facility.jpg" alt="Card image cap">
                                <div class="card-header">Chemistry Lab</div>
                            </div>
                        </div>
                    </div>
                    <a href="#" class="btn btn-light accordion" id="accordionexample1" data-toggle="collapse" data-target="#collapsetwo" aria-expanded="false" aria-controls="collapseOne">Events</a>
                    <div id="collapsetwo" class="collapse" aria-labelledby="headingOne" data-parent="#accordionexample1">
                        <div class="card ">
                            <img class="card-img-top cohesive_courosel" src="/img/Events 1.jpg" alt="Card image cap">
                            <small class="btn btn-light list-group-item">22 Nov 2018</small>
                            <div class="card-body">
                                <div class="list-group list-group-flush">
                                    <a class="btn btn-light list-group-item">Swamy's School Annual Day - 26-Aug-2016</a>
                                    <a class="btn btn-light list-group-item">Some interesting pictures. A few from the backstage as well,..</a>
                                    <a class="btn btn-light list-group-item">More</a>
                                </div>
                            </div>
                        </div>
                        <div class="card">
                            <img class="card-img-top cohesive_courosel" src="/img/frie.jpg" alt="Card image cap">
                            <small class="btn btn-light list-group-item">22 Nov 2018</small>
                            <div class="card-body">
                                <div class="list-group list-group-flush">
                                    <a class="btn btn-light list-group-item">Fire & Safety Workshop</a>
                                    <a class=" btn btn-light list-group-item">Fire prevention is everybody's job. Safety can distinguish you. Lack of safety can extinguish you. As part of safety measure, a two-day Fire and Safety training workshop was held at Swamy’s Group of Schools.</a>
                                    <a class="btn btn-light list-group-item">More</a>
                                </div>
                            </div>
                        </div>
                        <div class="card">
                            <img class="card-img-top cohesive_courosel" src="/img/yoga.jpg" alt="Card image cap">
                            <small class="btn btn-light list-group-item">22 feb 2018</small>
                            <div class="card-body">
                                <div class="list-group list-group-flush">
                                    <a href="#" class="btn btn-light list-group-item list-group ">Yoga Day on 22-Jun-2018</a>
                                    <a class="btn btn-light list-group-item">Yoga integrates the body, mind and soul. Yoga asanas help us in developing vigor, flexibility and confidence. To emphasize the importance of Yoga, International Yoga Day was celebrated on 22nd of June @ Swamy’s School.</a>
                                    <a class="btn btn-light list-group-item">More</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <a href="#" class="btn btn-light accordion" id="Management" data-toggle="collapse" data-target="#collapsethree" aria-expanded="false" aria-controls="collapseOne">Management</a>
                    <div id="collapsethree" class="collapse" aria-labelledby="headingOne" data-parent=#Management>
                        <div class="card-deck">
                            <div class="card">
                                <div class="card-header">Correspondent</div>
                                <img class="card-img-top cohesive_courosel" src="/img/corres.jpg" alt="Card image cap">
                                <div class="card-body">
                                    <div class="card-title">Smt. Brinda Venkataramanan</div>
                                    <div class="list-group list-group-flush">
                                        <a class="btn btn-light list-group-item">Our Correspondent Smt. Brinda Venkataramanan, B.Sc (Hons), M.Sc, a gold medalist, was an outstanding student, with a Post-graduate degree in science. An immensely versatile person, she has imbibed the best qualities of her father (our Founder). Her ability to readily adapt to the evolving educational </a>
                                    </div>
                                </div>
                            </div>
                            <div class="card">
                                <div class="card-header">Director</div>
                                <img class="card-img-top cohesive_courosel" src="/img/director.jpg" alt="Card image cap">
                                <div class="card-body">
                                    <div class="card-title">Mr. Girish Venkat</div>
                                    <div class="list-group list-group-flush">
                                        <a class="btn btn-light list-group-item">Our Director Mr. Girish Venkat is a master’s degree holder in computer applications and has been associated with Swamy’s School since 2012. Having had a career spanning more than 20 years as a software professional in managerial/technical leadership roles, he is currently responsible for monitoring day-to-day operations of the school. </a>
                                    </div>
                                </div>
                            </div>
                            <div class="card">
                                <div class="card-header">Principal</div>
                                <img class="card-img-top cohesive_courosel" src="/img/principal.jpg" alt="Card image cap">
                                <div class="card-body">
                                    <div class="card-title">Smt. Jaya Chandrasekhar</div>
                                    <div class="list-group list-group-flush">
                                        <a class="btn btn-light list-group-item">Smt. Jaya Chandrasekhar, Principal at Swamy’s School is a post graduate degree holder in Zoology and has a master’s degree in Education and Philosophy. She has been in the field of education for more than two decades in various capacities. Her curriculum knowledge has resulted in well laid out units and carefully constructed assessment tasks. She possesses an excellent understanding of pedagogy and displays strong administrative skills.</a>
                                    </div>
                                </div>
                            </div>
                            <div class="card">
                                <div class="card-header"> Founder</div>
                                <img class="card-img-top cohesive_courosel" src="/img/founder.jpg" alt="Card image cap">
                                <div class="card-body">
                                    <div class="card-title">Shri.P.Muthuswamy </div>
                                    <div class="list-group list-group-flush">
                                        <a class="btn btn-light list-group-item">Shri. P. Muthuswamy (1920-2000), the founder of Swamy’s School was born on 22nd June 1920. He had a bright educational career and graduated in Mathematics in the year 1940. He entered the Government service in the Department of P&T (Department of Post and Telegraphs, India) after obtaining a very high rank in the competitive examination. With his perseverance, he rose to the highest level at P&T as Director in the year 1978. He was a great human being, </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <a href="#" class="btn btn-light accordion" id="Contact" data-toggle="collapse" data-target="#collapsefour" aria-expanded="false" aria-controls="collapseOne">Contact</a>
                    <div id="collapsefour" class="collapse" aria-labelledby="headingOne" data-parent="#Contact">
                    </div>
                </div>
            </div>

    </body>

    </html>