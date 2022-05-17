<template>   
  <div style="width: 60%; margin:auto;" class="main-container" v-if="loaded">
    <h3 style="font-weight: bold; margin: 10px 0 0 0">{{rentingEntity.name}}</h3>
    <vue3-star-ratings style="margin-right: 5px" class="star-ratings" v-model="rentingEntity.rating" starSize="22"  :showControl=false :disableClick=true :step=0 />
    <h5 style="margin-top: 3px">({{rentingEntity.reviewsNumber}})</h5>
    <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img class="d-block w-100" alt="Active picture" v-bind:src="'data:image/jpeg;base64,' + pictures.at(0)">
        </div>
        <div class="carousel-item" v-for="(picture, index) in pictures.slice(1)" :key="index">
          <img class="d-block w-100" alt="Item picture" v-bind:src="'data:image/jpeg;base64,' + picture">
        </div>
      </div>
      <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>
    <div style="display:flex; margin-top: 10px">
      <div style="float:left; width: 65%; overflow: hidden; margin-top: 10px">
        <div><i class="bi bi-geo-alt-fill"></i> {{rentingEntity.address}}</div>
        <div><i class="bi bi-info-circle-fill"></i> {{rentingEntity.promoDescription}}</div>
        <div><i class="bi bi-exclamation-diamond-fill"></i> {{rentingEntity.behaviourRules}}</div>
        <div><i class="bi bi-cash"></i> {{rentingEntity.priceList}} per day</div>
        <div><b>Additional info: </b>{{rentingEntity.additionalInfo}}</div>
        <div><b>Cancellation conditions: </b>{{rentingEntity.cancellationConditions}}</div>
        <div v-if="this.displayType==0"><b>Capacity: </b>{{rentingEntity.roomsQuantity}} rooms, {{rentingEntity.bedsPerRoom}} beds per room</div>
        <div v-if="this.displayType==1">Type: {{rentingEntity.type}}</div>
        <div v-if="this.displayType==1">Length: {{rentingEntity.length}}</div>
        <div v-if="this.displayType==1">Engine Number: {{rentingEntity.engineNumber}}</div>
        <div v-if="this.displayType==1">Engine Power: {{rentingEntity.enginePower}}</div>
        <div v-if="this.displayType==1">Top Speed: {{rentingEntity.topSpeed}}</div>
        <div v-if="this.displayType==1">Client Limit: {{rentingEntity.clientLimit}}</div>
        <div v-if="this.displayType==2"><b>Instructor biography: </b>{{rentingEntity.instructorBiography}}</div>
        <div v-if="this.displayType==2"><b>Client limit: </b>{{rentingEntity.clientLimit}}</div>
      </div>
      <div style="float: right; width: 35%; overflow: hidden">
        <iframe style="width: 400px; height: 250px; border-radius: 5px" v-bind:src="'https://maps.google.com/maps?q=' + rentingEntity.address + '&t=&z=13&ie=UTF8&iwloc=&output=embed'"></iframe>
      </div>
    </div>
    <hr>
    <div style="margin-bottom: 30%">
      <h4 v-if="this.offers.length !== 0">Offers:</h4>
      <div style="display: flex; flex-direction: row; flex-wrap: wrap">
        <div style="border: 1px solid darkgrey; margin: 5px; border-radius: 5px; flex: 0 0 49%" v-for="(offer, index) in this.offers" @Click="selectOffer(offer, index)"
             v-bind:index="index" :key="offer.id" v-bind="{selected: selectedOffer.id===offer.id}">
          <div style="margin: 5px; display: flex">
            <div style="float: left; width: 60%">
              <div><i class="bi bi-geo-alt-fill"></i> {{offer.place}}</div>
              <div><i class="bi bi-cash"></i> {{offer.price}}</div>
              <div><b>Client limit: </b>{{offer.clientLimit}}</div>
              <div><b>Starting at: </b>{{offer.start}}</div>
              <div><b>Duration: </b>{{msToTime(offer.duration)}}</div>
              <div v-if="offer.additionalServices.length != 0"> <b>Additional Services:</b>
                <div style="margin-left: 10px" v-for="service in offer.additionalServices" :key="service"> {{service}}</div>
              </div>
            </div>
            <div style="float: right; width: 40%; align-items: center; display: flex; overflow: hidden">
              <button class="custom-btn button-primary" @click="makeReservation(offer, index)">Make a reservation</button>
            </div>
          </div>
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
      loaded:false
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
  makeReservation(offer, index){
    this.selectOffer(offer, index)
      axios.get(`/api/offers/${this.selectedOffer.id}/makeReservation`).then(response => console.log(response.data)).then(this.$toast.success("Reservation made"));
      this.selected=false;      
      this.$router.go(0);
  }
}
}
</script>

<style scoped>

.carousel-inner {
  border-radius: 5px;
  height: 500px;
}

div.star-ratings {
  padding: 0;
  margin: 0;
  float: left;
}

</style>