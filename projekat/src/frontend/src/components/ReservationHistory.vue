<template>
  <div>
    <div>
      <h3>Active Reservations</h3>
      <div>
        <input class="input-group-text" type="text" placeholder="Filter by name, Renting Entity, Price..." v-model="filterR"/>
      </div>
      <br>
      <div class="datepicker-sort">      
      <select class="form-select" v-model="sortTypeR" name="example">
        <option value=0>Place descending</option>
        <option value=1>Place ascending</option>
        <option value=2>Price descending</option>
        <option value=3>Price ascending</option>
      </select>
      </div>
      <div class="list-group" v-for="(reservation, index) in this.filteredReservations" @Click="selectReservation(reservation,index)"
            v-bind:index="index" :key="reservation.id"  v-bind="{selected: selectedReservation.id===reservation.id}">
          <br>
         <div class="entity-picture"><img v-bind:src="'data:image/jpeg;base64,' + reservation.img" style="width: 140px; height: 80px;"></div>
        {{reservation.rentingEntityName}} {{reservation.place}} People: {{reservation.clientLimit}} <label v-if="reservation.additionalServices.length != 0"> Additional Services: <label v-for="service in reservation.additionalServices" :key="service">
            {{service}}
          </label>
          </label>
            {{reservation.price}} EUR Starting Date: {{reservation.start}} Duration {{msToTime(reservation.duration)}}
      </div>
    </div>

    <div v-if="selectedR">

      
      CANCEL TODO
    </div>
    
    <div>
      <h3>Reservation History</h3>
      <div>
        <input class="input-group-text" type="text" placeholder="Filter by name, Renting Entity, Price..." v-model="filterH"/>
      </div>
      <br>
      <div class="datepicker-sort">      
      <select class="form-select" v-model="sortTypeH" name="example">
        <option value=0>Place descending</option>
        <option value=1>Place ascending</option>
        <option value=2>Price descending</option>
        <option value=3>Price ascending</option>
      </select>
      </div>
        <div class="list-group" v-for="(reservationHistory, index) in this.filteredReservationsHistory" @Click="selectReservationHistory(reservationHistory,index)"
            v-bind:index="index" :key="reservationHistory.id"  v-bind="{selected: selectedReservationHistory.id===reservationHistory.id}">
          <br>
          <div class="entity-picture"><img v-bind:src="'data:image/jpeg;base64,' + reservationHistory.img" style="width: 140px; height: 80px;"></div>
         {{reservationHistory.rentingEntityName}} {{reservationHistory.place}} 
        {{reservationHistory.price}} EUR Date: {{reservationHistory.start}} 
        </div>
      </div>
      <div v-if="selectedHistory" align='center'>
        <div align='left'>
          <vue3-star-ratings :showControl=false v-model="ratingRE"/>
          {{ratingRE}}          
          <textarea v-model="commentRatingRE" rows="4" cols="50" placeholder="Comment..."/>
          <button @click="rateRE()">Rate renting entity</button>
        </div>
        <div align='right'>
          <vue3-star-ratings :showControl=false v-model="ratingRO"/>
          {{ratingRO}} 
          <textarea v-model="commentRatingRO" rows="4" cols="50" placeholder="Comment..."/>
          <button @click="rateRO()">Rate owner</button>
        </div>
    </div>

  </div>
</template>

<script>
import axios from "axios"

export default {
  name: "ReservationHistory",
   data() {
    return {
      reservationsHistory:[],
      selectedReservationHistory:{},
      selectedHistory:false,
      reservations:[],
      selectedReservation:{},
      selectedR:false,
      filterR:"",
      filterH: "",
      ratingRE:5,
      commentRatingRE:"",
      commentRatingRO:"",
      ratingRO:5,
      sortTypeR:0,
      sortTypeH: 0,
      indexH:0,
      indexR:0,
    }
  },
  mounted() {
    
    axios.get("/api/clients/loggedClient/reservations",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.reservations=response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);          
          this.$router.push("/loginPage");
        }
       
      });
    axios.get("/api/clients/loggedClient/reservationHistory",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.reservationsHistory=response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);          
          this.$router.push("/loginPage");
        }
       
      });

  },
  computed: {
    filteredReservationsHistory() {
      return this.reservationsHistory.filter(row => {
        return this.includesH(row);
      }).sort((a,b)=>{
        return this.sortH(a,b);})
    },
    filteredReservations(){
       return this.reservations.filter(row => {
        return this.includesH(row);
      }).sort((a,b)=>{
        return this.sortR(a,b);})
    }},
  methods:{
    selectReservationHistory(reservation,index) {

      this.selectedReservationHistory = reservation;
      this.selectedHistory = true;
      
      this.indexH=index;
      

    },  
    selectReservation(reservation,index) {

      this.selectedReservation = reservation;
      this.selectedR = true;
      
      this.indexR=index;
      

    }, 
    rateRO(){
       const review={"id":0,'rentingEntityId':this.selectedReservationHistory.rentingEntityId,'rentingOwnerId':this.selectedReservationHistory.rentingEntityOwnerId,'clientId':0,'rating':this.ratingRO,'comment':this.commentRatingRO};
    axios.post("/api/clients/addReviewRO",review,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Review Added");}else{
      this.$toast.error("Error");
    }})

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
    this.selectedReservationHistory={};
    this.selectedHistory=false;
  },
  rateRE(){
    const review={"id":0,'rentingEntityId':this.selectedReservationHistory.rentingEntityId,'rentingOwnerId':this.selectedReservationHistory.rentingEntityOwnerId,'clientId':0,'rating':this.ratingRE,'comment':this.commentRatingRE};
    axios.post("/api/clients/addReviewRE",review,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Review Added");}else{
      this.$toast.error("Error");
    }})
  },
  includesH(row){
        
        const name = row.place.toString().toLowerCase();
        const rentingEntityName = row.rentingEntityName.toString().toLowerCase();
        const price = row.price.toString().toLowerCase();
        const searchTerm = this.filterH.toLowerCase();
        return (name.includes(searchTerm) || rentingEntityName.includes(searchTerm) || price.includes(searchTerm))
        
    },
    sort(a,b){
        if(this.sortTypeH==0){
          let fa = a.place.toLowerCase(), fb = b.place.toLowerCase();
          if (fa < fb) {
            return -1
          }
          if (fa > fb) {
            return 1
          }
          return 0
        }
        else if (this.sortTypeH == 1){
          let fa = a.place.toLowerCase(), fb = b.place.toLowerCase();
           if (fa > fb) {
            return -1
          }
          if (fa < fb) {
            return 1
          }
          return 0
        }
        if(this.sortTypeH==2){
        let fa =parseInt(a.price), fb =parseInt(b.price)
        
          if (fa > fb) {
            return -1
          }
          if (fa < fb) {
            return 1
          }
          return 0
        }
        else if (this.sortTypeH == 3){
          
          let fa = parseInt(a.price), fb =parseInt(b.price);
          
           if (fa < fb) {
            return -1
          }
          if (fa > fb) {
            return 1
          }
          return 0
        }

    },
  }
}
</script>

<style>

</style>