<template>
  <div>
    <div align='center'>
        <ul class="list-group" v-for="(reservation, index) in this.reservations" @Click="selectReservation(reservation,index)"
            v-bind:index="index" :key="reservation.id" v-bind="{selected: selectedReservation.id===reservation.id}">
          <br>
          
          <li class="list-group-item "><i class="bi bi-currency-dollar"></i>Place: {{reservation.place}} People: {{reservation.clientLimit}} <label v-if="reservation.additionalServices.length != 0"> Additional Services: <label v-for="service in reservation.additionalServices" :key="service">
            {{service}}
          </label>
          </label>
            Price: {{reservation.price}} Starting Date: {{reservation.start}} Duration {{msToTime(reservation.duration)}} </li>
        </ul>
      </div>
      <div v-if="selected" align='center'>
        <br>
        <button class="btn btn-primary" style="margin-right: 20px;" @click="makeReservation">Make A Reservation</button>
        <button class="btn btn-primary" @click="hide">Hide</button>
      </div>
  </div>
</template>

<script>
import axios from "axios"

export default {
  name: "ReservationHistory",
   data() {
    return {
      reservations:[],
      selectedReservation:{},
      selected:false,
    }
  },
  mounted() {
    axios.get("/api/clients/loggedClient/reservationHistory",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.reservations=response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);          
          this.$router.push("/loginPage");
        }
       
      });

  },
  methods:{
    selectReservation(reservation,index) {

      this.selectedReservation = reservation;
      this.selected = true;
      this.index=index;
      

    },   
    msToTime(seconds) {     
      seconds = Number(seconds)/1000;
      var d = Math.floor(seconds / (3600*24));
      var h = Math.floor(seconds % (3600*24) / 3600);


      var dDisplay = d > 0 ? d + (d == 1 ? " day, " : " days, ") : "";
      var hDisplay = h > 0 ? h + (h == 1 ? " hour, " : " hours, ") : "";

      return dDisplay + hDisplay;
  },
  hide(){
    this.selectedReservation={};
    this.selected=false;
  },
  }
}
</script>

<style>

</style>