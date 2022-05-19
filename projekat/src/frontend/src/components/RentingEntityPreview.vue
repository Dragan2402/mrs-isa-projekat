<template>   
  <div v-if="loaded" align='center'>
    <br>
      <h1>{{rentingEntity.name}}</h1>
    <div class="user-data-div" style="width: 50%">
      <p>Address: {{rentingEntity.address}}</p>
      <p>Promo Description: {{rentingEntity.promoDescription}}</p>
      <p>Behavior Rules: {{rentingEntity.behaviourRules}}</p>
      <p>Price: {{rentingEntity.priceList}}</p>
      <p>Additional Info: {{rentingEntity.additionalInfo}}</p>
      <p>Cancellation conditions: {{rentingEntity.cancellationConditions}}</p>

      <p v-if="this.displayType==0">Rooms Quantity: {{rentingEntity.roomsQuantity}}</p>
      <p v-if="this.displayType==0">Beds Per Room: {{rentingEntity.bedsPerRoom}}</p>
      <p v-if="this.displayType==1">Type: {{rentingEntity.type}}</p>
      <p v-if="this.displayType==1">Length: {{rentingEntity.length}}</p>
      <p v-if="this.displayType==1">Engine Number: {{rentingEntity.engineNumber}}</p>
      <p v-if="this.displayType==1">Engine Power: {{rentingEntity.enginePower}}</p>
      <p v-if="this.displayType==1">Top Speed: {{rentingEntity.topSpeed}}</p>
      <p v-if="this.displayType==1">Client Limit: {{rentingEntity.clientLimit}}</p>
      <p v-if="this.displayType==2">Instructor Biography: {{rentingEntity.instructorBiography}}</p>
      <p v-if="this.displayType==2">Client Limit: {{rentingEntity.clientLimit}}</p>
      <div align='center'>
        <ul class="list-group" v-for="(offer, index) in this.offers" @Click="selectOffer(offer,index)"
            v-bind:index="index" :key="offer.id" v-bind="{selected: selectedOffer.id===offer.id}">
          <br>
          <li class="list-group-item "><i class="bi bi-currency-dollar"></i>Place: {{offer.place}} People: {{offer.clientLimit}} <label v-if="offer.additionalServices.length != 0"> Additional Services: <label v-for="service in offer.additionalServices" :key="service">
            {{service}}
          </label>
          </label>
            Price: {{offer.price}} Starting Date: {{offer.start}} Duration {{msToTime(offer.duration)}}</li>
        </ul>

      </div>
      <div v-if="selected" align='center'>
        <br>
        <button class="btn btn-primary" style="margin-right: 20px;" @click="makeReservation">Make A Reservation</button>
        <button class="btn btn-primary" @click="hide">Hide</button>
      </div>
    </div>
    <div class="container" >
      <div class="row row-cols-4" >
        <div class="col p-3" v-for="(picture, index) in pictures" :key="index">
          <img v-bind:src="'data:image/jpeg;base64,' + picture">
        </div>
      </div>
    </div>
    </div>
</template>

<script>
import axios from "axios"

export default {
  name: "RentingEntityPreview",
  data(){
    return {
      id:0,
      displayType:0,
      rentingEntity: {},
      selectedOffer: {},
      selected:false,
      pictures:[],
      index:0,      
      offers: [],
      loaded:false,
    }
  },
  mounted(){
    // axios.get("api/ships/all").then( response => this.ships=response.data);
    this.displayType = this.$route.params.displayType;
    this.id=this.$route.params.id;
    this.loaded=false;
    if(this.displayType==0 && this.id != undefined){      
      const path="/api/vacation_houses/"+this.id;
      const pathOffers="/api/vacation_houses/"+this.id+"/offers";
      const pathPictures="/api/vacation_houses/"+this.id+"/pictures/all";
      axios.get(path).then( response => this.rentingEntity=response.data);
      axios.get(pathPictures).then(response => {
          for (let i = 0; i < response.data.length; i++) {
            this.pictures.push(response.data[i]);
          }
        });
      axios.get(pathOffers).then(response => this.offers=response.data);
      this.loaded=true;
      }
    else if(this.displayType==1 && this.id != undefined){
      const path="/api/ships/"+this.id;
      const pathOffers="/api/ships/"+this.id+"/offers";
      const pathPictures="/api/ships/"+this.id+"/pictures/all";
      axios.get(path).then( response => this.rentingEntity=response.data);
      axios.get(pathPictures).then(response => {
          for (let i = 0; i < response.data.length; i++) {
            this.pictures.push(response.data[i]);
          }
        });
      axios.get(pathOffers).then(response => this.offers=response.data);
      this.loaded=true;
    }
    else if(this.id != undefined){
      const path="/api/fishingClasses/"+this.id;
      const pathOffers="/api/fishingClasses/"+this.id+"/offers";
      const pathPictures="/api/fishingClasses/"+this.id+"/pictures/all";
      axios.get(path).then( response => this.rentingEntity=response.data);
      axios.get(pathPictures).then(response => {
          for (let i = 0; i < response.data.length; i++) {
            this.pictures.push(response.data[i]);
          }
        });
      axios.get(pathOffers).then(response => this.offers=response.data);
      this.loaded=true;
    }
},
methods:{
  selectOffer(offer,index) {

      this.selectedOffer = offer;
      this.selected = true;
      this.index=index;
      

    },   
    msToTime(duration) {
    var 
      
      minutes = Math.floor((duration / (1000 * 60)) % 60),
      hours = Math.floor((duration / (1000 * 60 * 60)) % 24);
 
    
    if(minutes>0){
      return hours+"H" + " " + minutes;
    }else{
       return hours+"H";
    }
  },
  hide(){
    this.selectedOffer={};
    this.selected=false;
  },
  makeReservation(){
   
     if(this.$root.loggedUser.penalties==3){
       this.$toast.error("You can not make a reservation. You have 3 penalties. Wait for next month.");
       return;
     }
      axios.get(`/api/offers/${this.selectedOffer.id}/makeReservation`,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {
        if(response.data==true){
          this.$toast.success("Reservation made")
        }else{
          this.$toast.error("You can not make a reservation. You have 3 penalties. Wait for next month.");
        }
      });
      
      this.selected=false;      
      this.$router.go(0);
  }
}
}
</script>

<style scoped>

.list-entities {
  text-align: center;
  width: 100%;
  height: 170px;
  border: 1px solid darkgrey;
  border-radius: 5px;
  overflow: hidden;
  margin-bottom: 10px;
  min-width: 800px;
}

img {
  object-fit: cover;
  width: 300px;
  height: 200px;
}

</style>