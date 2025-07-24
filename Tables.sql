USE team2;

drop table if exists MoviePlaylist;
drop table if exists UserRating;
drop table if exists Playlist;
drop table if exists Movies;
drop table if exists MovieGenres;
drop table if exists Users;

create table if not exists Users (
	uid integer primary key auto_increment,
    username varchar(20) not null,
    email varchar(20) not null,
    pwd varchar(20) not null
);

create table if not exists MovieGenres (
	mgid integer primary key auto_increment,
    genre varchar(20) not null
);

create table if not exists Movies (
	mid integer primary key auto_increment,
    title varchar(40) not null,
    date Date not null,
    mgid integer not null,
    synopsis varchar(100) not null,
    foreign key (mgid) references MovieGenres (mgid)
);

create table if not exists Playlist (
	pid integer primary key auto_increment,
    uid integer not null,
    pname varchar(20) not null,
    date Date not null,
    foreign key (uid) references Users (uid)
);

create table if not exists UserRating (
	urid integer primary key auto_increment,
    mid integer not null,
    rating integer not null,
    review varchar(100),
    foreign key (mid) references Movies (mid)
);

create table if not exists MoviePlaylist (
	mpid integer primary key auto_increment,
    pid integer not null,
    mid integer not null,
    foreign key (pid) references Playlist (pid),
    foreign key (mid) references Movies (mid)
);

