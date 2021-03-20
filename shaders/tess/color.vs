#version 330 core

in vec3 pos;
in vec4 color;

out vec4 vertexColor;

uniform float scale;
uniform mat4 projection;

void main()
{
	vertexColor = color;
	
	gl_Position = projection * vec4(scale, scale, scale, 1.0) * vec4(pos, 1.0);
}