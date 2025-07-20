create database if not exists propertyPortal;
use propertyPortal;

drop table if exists propertyPriceHistory, Offer, Inquiry, propertyFeature, Feature, propertyAgent, Property, Buyer, Agent, Agency;

-- Agency Table(3NF)
create table Agency (
	id int auto_increment primary key,
    name varchar(100) not null,
    address varchar(200) not null,
    phone varchar(200) not null,
    createdAt datetime default current_timestamp,
    updatedAt datetime default current_timestamp on update current_timestamp
);

-- Agent Table(3NF)
create table Agent (
	id int auto_increment primary key,
    agencyId int not null,
    name varchar(100) not null,
    email varchar(200) unique not null,
    phone varchar(200) unique not null,
    isActive boolean default true,
    totalDealsClosed int default 0,
    avgResponseTimeMinutes int default 0,
    createdAt datetime default current_timestamp,
    updatedAt datetime default current_timestamp on update current_timestamp,
    foreign key(agencyId) references Agency(id) on delete restrict
);

-- Buyer Table(3NF)
create table Buyer (
	id int auto_increment primary key,
    name varchar(100) not null,
    email varchar(200) unique not null,
    phone varchar(200) unique not null,
    isActive boolean default true,
    createdAt datetime default current_timestamp,
    updatedAt datetime default current_timestamp on update current_timestamp
);

-- Property Table(3NF)
create table Property (
	id int auto_increment primary key,
    title varchar(100) not null,
    description text not null,
    address varchar(200) not null,
    neighborhood varchar(100) not null,
    region varchar(100) not null,
    propertyType enum('apartment', 'house', 'condo', 'townhouse', 'land', 'commercial') not null,
    listingDate datetime not null,
    currentPrice bigint not null,
    status enum("available", "under_offer", "sold") not null default "available",
    soldDate datetime null,
    daysOnMarket int default 0,
    createdAt datetime default current_timestamp,
    updatedAt datetime default current_timestamp on update current_timestamp,
    fulltext(title, description, neighborhood, region)
);

-- Property Price History(3NF)
create table propertyPriceHistory (
    id int auto_increment primary key,
    propertyId int not null,
    price bigint not null,
    changedAt datetime not null default current_timestamp,
    priceType enum('listing', 'updated', 'sold') not null,
    changedBy int not null,
    foreign key(propertyId) references Property(id) on delete cascade,
    foreign key(changedBy) references Agent(id) on delete restrict
);

-- Property X Agent(3NF)
create table propertyAgent (
	propertyId int not null,
    agentId int not null,
    role enum('primary', 'secondary') not null default 'primary',
    assignedAt datetime default current_timestamp,
    primary key(propertyId, agentId),
	foreign key(propertyId) references Property(id) on delete cascade,
	foreign key(agentId) references Agent(id) on delete restrict
);

-- Feature Table(3NF)
create table Feature (
	id int auto_increment primary key,
    name varchar(100) unique not null,
    category enum("Indoor", "Outdoor", "Amenity") not null,
    description text,
    createdAt datetime default current_timestamp
);

-- Property X Feature(3NF)
create table propertyFeature (
	propertyId int not null,
    featureId int not null,
    primary key (propertyId, featureId),
    foreign key(propertyId) references Property(id) on delete cascade,
    foreign key(featureId) references Feature(id) on delete restrict
);

-- Offer Table(3NF)
create table Offer (
	id int auto_increment primary key,
    agentId int not null,
    buyerId int not null,
    propertyId int not null,
    offerAmount bigint not null,
    offerDate datetime not null default current_timestamp,
    status enum("pending","accepted","rejected","withdrawn") not null default "pending",
    responseDate datetime null,
    notes text,
    createdAt datetime default current_timestamp,
    updatedAt datetime default current_timestamp on update current_timestamp,
	foreign key(agentId) references Agent(id) on delete restrict,
    foreign key(buyerId) references Buyer(id) on delete restrict,
    foreign key(propertyId) references Property(id) on delete restrict
);

-- Inquiry Table(3NF)
create table Inquiry (
	id int auto_increment primary key,
    createdAt datetime default current_timestamp,
    message text not null,
    status enum("new", "responded", "closed") not null default "new",
    agentId int not null,
    buyerId int not null,
    propertyId int not null,
    respondedAt datetime null,
    closedAt datetime null,
    responseTimeMinutes int null,
    inquiryType enum("general", "viewing", "offer", "question") not null default "general",
    priority enum("low", "medium", "high") not null default "medium",
    updatedAt datetime default current_timestamp on update current_timestamp,
    foreign key(agentId) references Agent(id) on delete restrict,
    foreign key(buyerId) references Buyer(id) on delete restrict,
    foreign key(propertyId) references Property(id) on delete restrict
);

-- Agent Performance Tracking Table(3NF)
create table AgentPerformance (
    id int auto_increment primary key,
    agentId int not null,
    monthYear date not null,
    totalInquiries int default 0,
    respondedInquiries int default 0,
    avgResponseTimeMinutes int default 0,
    closedDeals int default 0,
    totalRevenue bigint default 0,
    createdAt datetime default current_timestamp,
    updatedAt datetime default current_timestamp on update current_timestamp,
    foreign key(agentId) references Agent(id) on delete cascade,
    unique key(agentId, monthYear)
);

-- Performance Indexes for common queries
-- Property queries
create index idxPropertyStatus on Property(status);
create index idxPropertyListingDate on Property(listingDate);
create index idxPropertyNeighborhood on Property(neighborhood);
create index idxPropertyRegion on Property(region);
create index idxPropertyType on Property(propertyType);
create index idxPropertySoldDate on Property(soldDate);
create index idxPropertyStatusListingDate on Property(status, listingDate);
create index idxPropertyNeighborhoodStatus on Property(neighborhood, status);
create index idxPropertyRegionType on Property(region, propertyType);
create index idxPropertyDaysOnMarket on Property(daysOnMarket);

-- Price history queries
create index idxPriceHistoryProperty on propertyPriceHistory(propertyId);
create index idxPriceHistoryDate on propertyPriceHistory(changedAt);
create index idxPriceHistoryType on propertyPriceHistory(priceType);

-- Agent performance queries
create index idxPropertyAgentAgent on propertyAgent(agentId);
create index idxPropertyAgentProperty on propertyAgent(propertyId);
create index idxPropertyAgentRole on propertyAgent(role);
create index idxAgentPerformanceAgent on AgentPerformance(agentId);
create index idxAgentPerformanceMonth on AgentPerformance(monthYear);

-- Offer queries
create index idxOfferProperty on Offer(propertyId);
create index idxOfferBuyer on Offer(buyerId);
create index idxOfferAgent on Offer(agentId);
create index idxOfferStatus on Offer(status);
create index idxOfferDate on Offer(offerDate);
create index idxOfferDateStatus on Offer(offerDate, status);
create index idxOfferPropertyStatus on Offer(propertyId, status);

-- Inquiry queries
create index idxInquiryProperty on Inquiry(propertyId);
create index idxInquiryBuyer on Inquiry(buyerId);
create index idxInquiryAgent on Inquiry(agentId);
create index idxInquiryStatus on Inquiry(status);
create index idxInquiryCreatedAt on Inquiry(createdAt);
create index idxInquiryClosedAt on Inquiry(closedAt);
create index idxInquiryDateProperty on Inquiry(createdAt, propertyId);
create index idxInquiryAgentStatus on Inquiry(agentId, status);
create index idxInquiryPropertyStatus on Inquiry(propertyId, status);
create index idxInquiryType on Inquiry(inquiryType);
create index idxInquiryPriority on Inquiry(priority);
create index idxInquiryResponseTime on Inquiry(responseTimeMinutes);

-- Feature queries
create index idxPropertyFeatureProperty on propertyFeature(propertyId);
create index idxPropertyFeatureFeature on propertyFeature(featureId);

