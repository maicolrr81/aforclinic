module.exports = {
  apps: [
    {
      name: 'admin',
      cwd: '/var/www/admin',
      script: './.output/server/index.mjs',
      env: {
        PORT: 4000,
      },
      interpreter: 'node',
      instances: 'max',
      exec_mode: 'cluster',
      autorestart: true,
      watch: false
    },
    {
      name: 'api',
      cwd: '/var/www/api',
      script: 'java',
      args: '-jar aforclinic-api-0.0.1-SNAPSHOT.jar --server.port=8080 --spring.profiles.active=production',
      exec_mode: 'fork',
      autorestart: true,
      watch: false
    },
    {
      name: 'web',
      cwd: '/var/www/web',
      script: './.output/server/index.mjs',
      env: {
        PORT: 3000,
      },
      interpreter: 'node',
      instances: 'max',
      exec_mode: 'cluster',
      autorestart: true,
      watch: false
    },
  ]
}

