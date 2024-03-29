#version 330 core

layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 tex_coord;

out vec2 texCoord;

void main()
{
	gl_Position = vec4(pos, 1.0);
	texCoord = tex_coord;
}