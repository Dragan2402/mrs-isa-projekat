<template>
  <div class="main-container" v-if="loaded">
    <div class="heading-flex">
      <div class="heading-left">
        <h3 class="main-heading">{{rentingEntity.name}}</h3>
        <vue3-star-ratings v-if="loaded" class="star-ratings"  v-model="rentingEntity.rating" starSize="22"  :showControl=false :disableClick=true :step=0 />
        <h5 class="star-heading">({{rentingEntity.reviewsNumber}})</h5>
      </div>
      <div class="heading-right">
        <div class="heading-right-buttons">
          <button style="margin-right: 20px" class="custom-btn button-primary" v-if="!isSubscribed" @click="subscribe()">Subscribe</button>
          <button style="margin-right: 20px" class="custom-btn button-primary" v-else @click="unSubscribe()">Unsubscribe</button>
          <button type="button" @click="toggleMakingReservation" class="custom-btn button-primary" data-bs-toggle="modal" data-bs-target="#modalReservation">Custom reservation</button>
        </div>
      </div>
    </div>
    <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img class="carousel-image d-block w-100" alt="Active picture" v-bind:src="'data:image/jpeg;base64,' + pictures.at(0)">
        </div>
        <div class="carousel-item" v-for="(picture, index) in pictures.slice(1)" :key="index">
          <img class="carousel-image d-block w-100" alt="Item picture" v-bind:src="'data:image/jpeg;base64,' + picture">
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
    <div class="main-description">
      <div class="inner-description">
        <div><b>Address: </b> {{rentingEntity.address}}</div>
        <div><b>Description: </b> {{rentingEntity.promoDescription}}</div>
        <div><b>Behaviour rules: </b> {{rentingEntity.behaviourRules}}</div>
        <div><b>Price: </b> {{rentingEntity.priceList}}&euro;</div>
        <div><b>Additional info: </b>{{rentingEntity.additionalInfo}}</div>
        <div><b>Cancellation conditions: </b>{{rentingEntity.cancellationConditions}}</div>
        <div v-if="this.displayType==0"><b>Capacity: </b>{{rentingEntity.roomsQuantity}} rooms, {{rentingEntity.bedsPerRoom}} beds per room</div>
        <div v-if="this.displayType==1"><b>Type: </b>{{rentingEntity.type}}</div>
        <div v-if="this.displayType==1"><b>Length: </b>{{rentingEntity.length}}</div>
        <div v-if="this.displayType==1"><b>Engine Number: </b>{{rentingEntity.engineNumber}}</div>
        <div v-if="this.displayType==1"><b>Engine Power: </b>{{rentingEntity.enginePower}}</div>
        <div v-if="this.displayType==1"><b>Top Speed: </b>{{rentingEntity.topSpeed}}</div>
        <div v-if="this.displayType==1"><b>Client Limit: </b>{{rentingEntity.clientLimit}}</div>
        <div v-if="this.displayType==2"><b>Instructor biography: </b>{{rentingEntity.instructorBiography}}</div>
        <div v-if="this.displayType==2"><b>Client limit: </b>{{rentingEntity.clientLimit}}</div>
      </div>
      <div class="google-map-container">
        <iframe class="google-map" v-bind:src="'https://maps.google.com/maps?q=' + rentingEntity.address + '&t=&z=13&ie=UTF8&iwloc=&output=embed'"></iframe>
      </div>
    </div>
    <hr>
    <div class="main-offer-container">
      <h4 v-if="this.offers.length !== 0">Offers:</h4>
      <div class="inner-offer-container">
        <div class="offer" v-for="(offer, index) in this.offers" v-bind:index="index" :key="offer.id">
          <div class="offer-description">
            <div class="offer-left">
              <div><b>Address: </b> {{offer.place}}</div>
              <div><b>Price: </b> {{offer.price}}</div>
              <div><b>Client limit: </b>{{offer.clientLimit}}</div>
              <div><b>Starting at: </b>{{offer.start}}</div>
              <div><b>Duration: </b>{{msToTime(offer.duration)}}</div>
              <div v-if="offer.additionalServices.length != 0"> <b>Additional Services: </b>
                <span v-for="service in offer.additionalServices.slice(0,-1)" :key="service"> {{service}}, </span>
                <span>{{offer.additionalServices.at(-1)}}</span>
              </div>
            </div>
            <div class="offer-right">
              <button class="custom-btn button-primary" @click="makeReservation(offer,index)">Make a reservation</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="modal fade" id="modalReservation" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <v-date-picker style="width: 100%" v-model="range" mode='dateTime' color="blue" is24hr :minute-increment="30" is-range  :min-date=minDate :max-date=maxDate :attributes='attributes'>
          </v-date-picker>
          <div style="margin-top: 10px" v-for="service in additionalServices" :key="service">
            <span style="margin-right: 10px">{{service}} </span>
            <input type="checkbox" v-model="selectedServices" :value="service" checked/>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="custom-btn button-primary" id="close-btn" data-bs-dismiss="modal">Close</button>
          <button type="button" @click="confirmReservation" class="custom-btn button-primary" data-bs-dismiss="modal">Confirm</button>
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
        let takenDatesPoppers = [];
    return {
      id:0,
      displayType:0,
      rentingEntity: {},
      selectedOffer: {},
      selected:false,
      date:null,
      pictures:[],
      isSubscribed:false,
      index:0,     
      offers: [],
      additionalServices:[],
      selectedServices:[],
      loaded:false,
      minDate:null,
      maxDate:null,  
      takenDates:[],
      range: {
        start: null,
        end: null,
      },
      incId: takenDatesPoppers.length,
      takenDatesPoppers,
      bars:[]
    }
  },
  mounted(){
    this.displayType = this.$route.params.displayType;
    this.id=this.$route.params.id;
    this.loaded=false;
    let path,pathOffers,pathPictures;
    if(this.displayType==0 && this.id != undefined){      
      path="/api/vacationHouses/anyUser/"+this.id;
      pathOffers="/api/vacationHouses/anyUser/"+this.id+"/offers";
      pathPictures="/api/vacationHouses/anyUser/"+this.id+"/pictures/all";
      }
    else if(this.displayType==1 && this.id != undefined){
      path="/api/ships/"+this.id;
      pathOffers="/api/ships/"+this.id+"/offers";
      pathPictures="/api/ships/"+this.id+"/pictures/all";
    }
    else if(this.id != undefined){
      path="/api/fishingClasses/"+this.id;
      pathOffers="/api/fishingClasses/"+this.id+"/offers";
      pathPictures="/api/fishingClasses/"+this.id+"/pictures/all";
    }

    axios.get(path).then( response => {
      this.rentingEntity=response.data;
      this.loaded=true
    });


    axios.get(pathPictures).then(response => {
          for (let i = 0; i < response.data.length; i++) {
            this.pictures.push(response.data[i]);
          }
        });
    axios.get(pathOffers).then(response => this.offers=response.data);

    

    if(localStorage.getItem("jwt")!="null"){
      if(this.$root.loggedUser.accountType=="CLIENT"){
        const path="/api/clients/isSubscribed/"+this.id;
        axios.get(path,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} } ).then(response =>{
        this.isSubscribed=response.data;
        });
      }
    }

},
computed: {
    attributes() {
      return [
        ...this.bars,       
        // Attributes for todos
        ...this.takenDatesPoppers.map(todo => ({
          dates: todo.dates,
          dot: {
            color: todo.color,
            class: todo.isComplete ? 'opacity-75' : '',
          },
          popover: {
            label: todo.description,
          },
          customData: todo,
        })),
      ];
    },
  },
methods:{
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

  dateFromLocal(dateString){
      if(dateString==null){
        return new Date();
      }
      
      const parts=dateString.split(" ");
      
      if( parts.length !=2){
        return new Date();
      }
      const dateParts= parts[0].split(".");
      
      if( dateParts.length !=3){
        return new Date();
      }
      const hourParts=parts[1].split(":");
      
      if( hourParts.length !=2){
        return new Date();
      }
      
      const day = dateParts[0];
      const month = dateParts[1];
      const year = dateParts[2];
      const hours = hourParts[0];
      const minutes = hourParts[1];
      // new Date(year, month, day, hours, minutes, seconds, milliseconds)
      const date= new Date(year,month-1,day,hours,minutes);
      return date;
    },
  subscribe(){
    if(localStorage.getItem("jwt")=="null"){
      this.$router.push("/loginPage");
      return;
    }
    if(this.$root.loggedUser.accountType!="CLIENT"){
      this.$toast.error("Only available for clients");
      return;
    }
    axios.put(`/api/clients/subscribe`,this.rentingEntity,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response =>{
      if(response.data==true){
        this.$toast.success("Subscribed");
        this.isSubscribed=true;
      }else{
        this.$toast.error("Error");
        this.isSubscribed=false;
      }
    });
  },
  unSubscribe(){
    if(localStorage.getItem("jwt")=="null"){
      this.$router.push("/loginPage");
      return;
    }
    if(this.$root.loggedUser.accountType!="CLIENT"){
      this.$toast.error("Only available for clients");
      return;
    }
    axios.put(`/api/clients/unSubscribe`,this.rentingEntity,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response =>{
      if(response.data==true){
        this.$toast.success("Unsubscribed");
        this.isSubscribed=false;
      }else{
        this.$toast.error("Error");
        this.isSubscribed=true;
      }
    });
  },
  generateBar(takenDTO){
    let color="red";
    if(takenDTO.type=="Offer"){
      color="blue";
    }
    let datesBars=[]
    let takenTo= this.dateFromLocal(takenDTO.takenTo);
    for (var d = this.dateFromLocal(takenDTO.takenFrom); d <= takenTo; d.setDate(d.getDate() + 1)) {
        const date = new Date(d);
        datesBars.push(date);
    }
    const bar={
   
          dates:datesBars,         
          bar:color,
    };
    this.bars.push(bar);
  },
  generatePopover(takenDTO){
    let color="red";
    if(takenDTO.type=="Offer"){
      color="blue";
    }
    const takenStart={
        description: takenDTO.startsAt,
        isComplete: false,
        dates: this.dateFromLocal(takenDTO.takenFrom), // Every Friday
        color: color,
      };
    const takenEnd={
        description:  takenDTO.endsAt,
        isComplete: false,
        dates: this.dateFromLocal(takenDTO.takenTo), 
        color: color,
      };
    this.takenDatesPoppers.push(takenStart);
    this.takenDatesPoppers.push(takenEnd);
  },
  takeCalendar(takenDates){
    this.bars= [];
    this.takenDatesPoppers=[];
    takenDates.forEach(this.generateBar);
    takenDates.forEach(this.generatePopover);
    this.takenDates=takenDates;
  },
  toggleMakingReservation(){
    if(localStorage.getItem("jwt")=="null"){
      this.$router.push("/loginPage");
      return;
    }
    if(this.$root.loggedUser.accountType!="CLIENT"){
      this.$toast.error("Only available for clients");
      return;
    }

    axios.get(`/api/clients/rentingEntityAvailability/${this.rentingEntity.id}`,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => 
    {
      this.takeCalendar(response.data);
          const availableFromDate = this.dateFromLocal(this.rentingEntity.availableFrom);
    const availableToDate = this.dateFromLocal(this.rentingEntity.availableTo);
    const now = new Date();

    if(availableFromDate<=now){
      this.minDate=now;
      
    }else{
      this.minDate = availableFromDate;
    }

    if(availableToDate <= now){    
      this.maxDate=now;
    }else{
      this.maxDate = availableToDate;
    }
    
 
    
    //this.minDate = this.dateFromLocal(this.rentingEntity.availableFrom);
    //this.maxDate = this.dateFromLocal(this.rentingEntity.availableTo);
    let availableFrom="Renting entity available from "+this.minDate.getHours()+":"+this.minDate.getMinutes();
    let availableTo="Renting entity available till "+this.minDate.getHours()+":"+this.minDate.getMinutes();
    if(availableToDate < now && availableFromDate < now){
      this.$toast.error("Entity is not available currently");  
      availableFrom="Not available";
      availableTo="Not available";
    }
    const takenStart={
        description: availableFrom,
        isComplete: false,
        dates: this.minDate, 
        color: "green",
      };
    const takenEnd={
         description: availableTo,
        isComplete: false,
        dates: this.maxDate, 
        color: "green",
      };
    this.takenDatesPoppers.push(takenStart);
    this.takenDatesPoppers.push(takenEnd);
      
      });
    let servicesPath;
    if(this.displayType==0){
      servicesPath="/api/vacationHouseOwners/getServices/"+this.rentingEntity.ownerId;
    }else if(this.displayType==1){
      servicesPath="/api/shipOwners/getServices/"+this.rentingEntity.ownerId;
    }
    else{
      servicesPath="/api/fishingInstructors/getServices/"+this.rentingEntity.ownerId;
    }
    axios.get(servicesPath,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => this.additionalServices=response.data);


  },
  freeSlot(start,end){
    for (let i = 0; i < this.takenDates.length; i++) {
        const takenFrom=this.dateFromLocal(this.takenDates[i].takenFrom);
        const takenTo = this.dateFromLocal(this.takenDates[i].takenTo);
        if( (start>=takenFrom && end <=takenTo) || (start<=takenFrom && end >=takenTo) || (start>=takenFrom && start<=takenTo) || (end <= takenTo && end >= takenFrom) || start<this.minDate || end>this.maxDate){
          return false;
        } 
    } 
    return true;
  },
  confirmReservation(){
    const start=this.range.start;
    const end=this.range.end;
  
    if(this.freeSlot(start,end)){
      

      let clientLimit=0;
      if(this.displayType==0){
        clientLimit=this.rentingEntity.roomsQuantity*this.rentingEntity.bedsPerRoom;
      }else{
        clientLimit=this.rentingEntity.clientLimit;
      }
      if(this.$root.loggedUser.penalties==3){
       this.$toast.error("You can not make a reservation. You have 3 penalties. Wait for next month.");
       return;
      }
      if(this.minDate == this.maxDate){
        return;
      }
      const reservation={        
        "place":this.rentingEntity.address,
        "clientLimit": clientLimit,
        "additionalServices":this.selectedServices,
        "price":this.rentingEntity.priceList,
        "rentingEntityId":this.rentingEntity.id,
        "start":start,
        "end":end
      };
      axios.post(`/api/clients/makeReservationFull`,reservation,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(
        response => {
          if(response.data==true){
            this.$toast.success("Reservation made");
          }else{
            if(response.status == 423){
                this.$toast.error("Try later.");
            }else{
              this.$toast.error("Error");
            }
          }
        }
      );
    }else{
      this.$toast.error("Select free date");
    }
  },

  makeReservation(offer,index){

    if(localStorage.getItem("jwt")=="null"){
      this.$router.push("/loginPage");
      return;
    }
    if(this.$root.loggedUser.accountType!="CLIENT"){
      this.$toast.error("Only available for clients");
      return;
    }
     if(this.$root.loggedUser.penalties==3){
       this.$toast.error("You can not make a reservation. You have 3 penalties. Wait for next month.");
       return;
     }
      axios.post(`/api/offers/makeQuickReservation`,offer,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {
        if(response.data==true){
           this.$toast.success("Reservation made");    
           this.offers.splice(index,1);
        }else{
          this.$toast.error("You can not make a reservation. You have 3 penalties. Wait for next month.");
        }
      });
  },
  updateRating(){
    this.rating=4.1;
  }

}
}
</script>

<style scoped>

.inner-offer-container {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-between;
}

.offer {
  min-width: 100%;
  padding: 10px;
  border: 1px solid darkgrey;
  border-radius: 5px;
  margin-bottom: 10px;
}

.offer-description {
  margin: 5px;
  display: flex;
}

.offer-left {
  float: left;
  width: 60%;
}

.offer-right {
  float: right;
  margin: auto;
  text-align: center;
  width: 40%;
  overflow: hidden;
}

</style>