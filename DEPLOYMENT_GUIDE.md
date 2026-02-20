# Portfolio Deployment Guide

This project is now ready for deployment to GitHub, Railway (Backend), and Netlify (Frontend).

## 1. GitHub Repository
The code has been successfully pushed to:
**https://github.com/prashant-java-dev/prashant-fullstack-portfolio**

## 2. MongoDB Setup
You should use a MongoDB instance. You can create a free cluster on [MongoDB Atlas](https://www.mongodb.com/cloud/atlas) or use the Railway MongoDB plugin.
- **Connection URI**: `mongodb+srv://<username>:<password>@<cluster>.mongodb.net/portfolio?retryWrites=true&w=majority`

## 3. Railway (Backend Deployment)
1. Log in to [Railway](https://railway.app/).
2. Click **New Project** -> **Deploy from GitHub repo**.
3. Select `prashant-fullstack-portfolio`.
4. **IMPORTANT**: In the service settings, set the **Root Directory** to `backend/prashant_portfolio`.
5. Add the following **Environment Variables**:
   - `MONGO_URI`: Your MongoDB connection string.
   - `MAIL_HOST`: `smtp.gmail.com`
   - `MAIL_PORT`: `587`
   - `MAIL_USERNAME`: `jii52580@gmail.com`
   - `MAIL_PASSWORD`: `klrj ohyj qgni wffe` (Your App Password)
   - `MAIL_FROM`: `jii52580@gmail.com`
   - `MAIL_TO`: `jii52580@gmail.com`
   - `ALLOWED_ORIGINS`: Your Netlify URL (e.g., `https://prashant-portfolio.netlify.app`)
   - `LOGGING_LEVEL`: `INFO`

## 4. Netlify (Frontend Deployment)
1. Log in to [Netlify](https://www.netlify.com/).
2. Click **Add new site** -> **Import an existing project**.
3. Select GitHub and choose `prashant-fullstack-portfolio`.
4. Netlify should automatically detect the `netlify.toml` file. If not, use these settings:
   - **Base directory**: `frontend`
   - **Build command**: `npm run build`
   - **Publish directory**: `dist`
5. Add the following **Environment Variable**:
   - `VITE_API_URL`: Your Railway Backend URL (e.g., `https://portfolio-production.up.railway.app/api`)

## 5. Summary of Changes
- **MongoDB Integration**: Added `spring-boot-starter-data-mongodb` and created `ContactMessage` model/repository to save contact form submissions.
- **Backend Refactor**: Updated `ContactController` to save data to MongoDB before sending an email.
- **Deployment Config**: Added `netlify.toml` for easy Netlify setup.
- **API Flexibility**: Updated frontend to use `import.meta.env.VITE_API_URL`.
