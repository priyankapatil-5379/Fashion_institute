document.addEventListener('DOMContentLoaded', function() {
    // Initialize Enrollment Chart
    const ctx = document.getElementById('enrollmentChart').getContext('2d');
    
    // Custom Gradient for the Chart (Golden Orange)
    const gradient = ctx.createLinearGradient(0, 0, 0, 400);
    gradient.addColorStop(0, 'rgba(250, 137, 26, 0.4)');
    gradient.addColorStop(1, 'rgba(250, 137, 26, 0)');

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
            datasets: [{
                label: 'New Enrollments',
                data: [65, 78, 110, 85, 145, 190],
                borderColor: '#fa891a',
                borderWidth: 3,
                backgroundColor: gradient,
                fill: true,
                tension: 0.4,
                pointRadius: 4,
                pointBackgroundColor: '#fa891a',
                pointBorderColor: '#fff',
                pointHoverRadius: 6
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false
                },
                tooltip: {
                    backgroundColor: '#2b1b10',
                    titleColor: '#fff',
                    bodyColor: '#fff',
                    borderColor: 'rgba(255, 255, 255, 0.1)',
                    borderWidth: 1,
                    padding: 10,
                    displayColors: false
                }
            },
            scales: {
                y: {
                    grid: {
                        color: 'rgba(0, 0, 0, 0.05)',
                        drawBorder: false
                    },
                    ticks: {
                        color: '#6b7280',
                        font: {
                            family: 'Outfit'
                        }
                    }
                },
                x: {
                    grid: {
                        display: false
                    },
                    ticks: {
                        color: '#6b7280',
                        font: {
                            family: 'Outfit'
                        }
                    }
                }
            }
        }
    });

    // Handle Nav Link Clicks
    const navLinks = document.querySelectorAll('.nav-links a');
    navLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            // Only handle internal '#' links
            if (this.getAttribute('href') === '#') {
                e.preventDefault();
                navLinks.forEach(l => l.classList.remove('active'));
                this.classList.add('active');
            }
        });
    });
});
