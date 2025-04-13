FROM node:22

WORKDIR /app

COPY package*.json ./

RUN npm install

# Copy all the files over
COPY . .

EXPOSE 3000

# Run the command
CMD ["npm", "start"]
