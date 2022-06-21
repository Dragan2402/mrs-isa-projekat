<template>
      <div id="error-page">
         <div class="content">
            <h2 class="header" data-text="404">               
            </h2>
            <h4 >
               Opps! Page not found
            </h4>
            <p>
               Sorry, the page you're looking for doesn't exist. If you think something is broken, report a problem.
            </p>
            <div class="btns">
               <a @click="returnHome()">return home</a>
            </div>
         </div>
      </div>
</template>

<script>

export default {
  name: "NotFoundPage",
  methods:{
    returnHome(){
      this.$router.push("/");
      this.signIn=true;
      this.signUp=true;      
    }
  }

}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Poppins:400,500,600,700&display=swap');
*{
  margin: 0;
  padding: 0;
  outline: none;

  font-family: 'Poppins', sans-serif;
}

#error-page{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: #fff;
}
#error-page .content{
  text-align: center;
}
.content h2.header{
  font-size: 18vw;
  line-height: 1em;
}
.content h2.header:after{
  content: attr(data-text);
  top: 0;
  left: 0;
  right: 0;
  background: -webkit-repeating-linear-gradient(-45deg, #004661, #056085, #0f3c4e, #1a9ccf, #092a36, rgb(17, 103, 160), #103a4b);
  background-size: 400%;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;

  animation: animate 10s ease-in-out infinite;
}
@keyframes animate {
  0%{
    background-position: 0 0;
  }
  25%{
    background-position: 100% 0;
  }
  50%{
    background-position: 100% 100%;
  }
  75%{
    background-position: 0% 100%;
  }
  100%{
    background-position: 0% 0%;
  }
}
.content h4{
  font-size: 1.5em;
  margin-bottom: 20px;
  text-transform: uppercase;
  color: #000;
  font-size: 2em;

}

.content p{
  font-size: 1.2em;
  color: #0d0d0d;
}
.content .btns{
  margin: 25px 0;
  display: inline-flex;
}
.content .btns a{
  display: inline-block;
  margin: 0 10px;
  text-decoration: none;
  border: 2px solid white;
  background-color: #004661;
  color: white;
  font-weight: 500;
  padding: 10px 25px;
  border-radius: 25px;
  text-transform: uppercase;
  transition: all 0.3s ease;
}
.content .btns a:hover{
  background: #196887;
  cursor:pointer;
  color: #fff;
}
</style>