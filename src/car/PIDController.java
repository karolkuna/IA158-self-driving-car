package car;

class PIDController {
	private float m_kp = 0f;
	private float m_ki = 0f;
	private float m_kd = 0f;
	private float prev = 0f;
	private float integral = 0f;
	
	public PIDController(float kp, float kd, float ki) {
		m_kp = kp;
		m_ki = ki;
		m_kd = kd;
	}
	
	public float control(float error) {
		integral = integral + error;
		float value = m_kp*error + m_kd*(error-prev) + m_ki*integral;
		prev = error;
		return value;
	}
}