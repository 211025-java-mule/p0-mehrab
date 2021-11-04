# Nasa APOD API tool
Description here

# Features 
- Can parse cli args:
    - `-t` for type of output
    - `--api` for NASA api to use
- Can save APOD results to a file
- Can ...

# Running
Setup DB
>docker run -it --rm -p 5432:5432 -e POSTGRES_USER=nasa -e POSTGRES_PASSWORD=nasa --name nasadb -v $(pwd)/schema.sql:/docker-entrypoint-initdb.d/schema.sql postgres

On Windows
>docker run -it --rm -p 5432:5432 -e POSTGRES_USER=nasa -e POSTGRES_PASSWORD=nasa --name nasadb -v //c/Users/MehrabRahman/p0-mehrab/schema.sql:/docker-entrypoint-initdb.d/schema.sql postgres

Debug
>mvn exec:java -q

